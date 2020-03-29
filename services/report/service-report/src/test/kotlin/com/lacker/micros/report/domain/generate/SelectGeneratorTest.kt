package com.lacker.micros.report.domain.generate

import com.lacker.micros.report.domain.datasource.DataColumn
import com.lacker.micros.report.domain.datasource.DataColumnOfAlias
import com.lacker.micros.report.domain.datasource.DataTable
import com.lacker.micros.report.domain.define.Join
import com.lacker.micros.report.domain.define.OrderBy
import com.lacker.micros.report.domain.define.Report
import com.lacker.micros.report.domain.define.ReportColumn
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.spyk
import net.sf.jsqlparser.parser.CCJSqlParser
import net.sf.jsqlparser.parser.CCJSqlParserUtil
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Ignore
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
internal class SelectGeneratorTest {

    private val generator = SelectGenerator()

    private lateinit var report: Report

    @BeforeEach
    fun setup() {
        val dataColumns = listOf(
                DataColumn(11),
                DataColumn(12),
                DataColumn(13)
        )
        val dataTable = DataTable(1, dataColumns[0].id.toString())

        report = spyk(Report(2, "", dataTable, "", ""))

        every { report.columns } returns listOf(
                ReportColumn(22, "", DataColumnOfAlias(report.id, dataColumns[1].id.toString())),
                ReportColumn(23, "", DataColumnOfAlias(report.id, dataColumns[2].id.toString()))
        )
    }

    @Nested
    inner class SelectTest {

        @Test
        fun `base select`() {
            assertSelect {
                """
                    SELECT `2`.`11` AS `id`, `2`.`12` AS `22`, `2`.`13` AS `23`
                    FROM `1` AS `2`
                    ORDER BY `id` DESC
                    LIMIT 0, 10
                """.trimIndent()
            }
        }

        @Test
        fun `with joins`() {
            val dataColumn31 = DataColumn(31)
            val table3 = DataTable(3, dataColumn31.id.toString())
            val reportColumns = report.columns.toTypedArray()
            every { report.columns } returns listOf(
                    *reportColumns,
                    ReportColumn(24, "", DataColumnOfAlias(report.id, dataColumn31.id.toString()))

            )
            every { report.joins } returns listOf(
                    Join(4, report.table, table3, report.columns[0].expression, DataColumnOfAlias(4, dataColumn31.id.toString()))
            )

            assertSelect {
                """
                    SELECT `2`.`11` AS `id`, `2`.`12` AS `22`, `2`.`13` AS `23`, `2`.`31` AS `24`
                    FROM `1` AS `2`
                    LEFT JOIN `3` AS `4` ON `2`.`12` = `4`.`31`
                    ORDER BY `id` DESC
                    LIMIT 0, 10
                """.trimIndent()
            }
        }

        @Test
        fun `with filters`() {
            every { report.filters } returns listOf(
                    mockk { every { expression } returns report.columns[0].expression }
            )

            assertSelect {
                """
                    SELECT `2`.`11` AS `id`, `2`.`12` AS `22`, `2`.`13` AS `23`
                    FROM `1` AS `2`
                    WHERE `2`.`12` = ?
                    ORDER BY `id` DESC
                    LIMIT 0, 10
                """.trimIndent()
            }
        }

        @Test
        fun `with orders`() {
            every { report.orderBies } returns listOf(
                    OrderBy(0, report.columns[0]),
                    OrderBy(0, report.columns[1], "DESC")
            )

            assertSelect {
                """
                    SELECT `2`.`11` AS `id`, `2`.`12` AS `22`, `2`.`13` AS `23`
                    FROM `1` AS `2`
                    ORDER BY `22` ASC, `23` DESC
                    LIMIT 0, 10
                """.trimIndent()
            }
        }

        @Test
        fun `select from another report`() {
            val report3 = spyk(Report(3, "", report, "", ""))
            every { report3.columns } returns listOf(
                    ReportColumn(32, "", DataColumnOfAlias(report3.id, report.columns[0].id.toString())),
                    ReportColumn(33, "", DataColumnOfAlias(report3.id, report.columns[1].id.toString()))
            )

            assertSelect(report3) {
                """
                    WITH `2` AS
                    (SELECT `2`.`11` AS `id`, `2`.`12` AS `22`, `2`.`13` AS `23`
                    FROM `1` AS `2`
                    ORDER BY `id` DESC)
                    SELECT `3`.`id` AS `id`, `3`.`22` AS `32`, `3`.`23` AS `33`
                    FROM `2` AS `3`
                    ORDER BY `id` DESC
                    LIMIT 0, 10
                """.trimIndent()
            }
        }

        @Ignore
        fun `select from nested report`() {
            val report3 = spyk(Report(3, "", report, "", ""))
            every { report3.columns } returns listOf(
                    ReportColumn(32, "", DataColumnOfAlias(report3.id, report.columns[0].id.toString())),
                    ReportColumn(33, "", DataColumnOfAlias(report3.id, report.columns[1].id.toString()))
            )
            val report4 = spyk(Report(4, "", report3, "", ""))
            every { report4.columns } returns listOf(
                    ReportColumn(42, "", DataColumnOfAlias(report4.id, report3.columns[0].id.toString())),
                    ReportColumn(43, "", DataColumnOfAlias(report4.id, report3.columns[1].id.toString()))
            )

            assertSelect(report4) {
                """
                    WITH `3` AS
                    (WITH `2` AS
                    (SELECT `2`.`11` AS `id`, `2`.`12` AS `22`, `2`.`13` AS `23`
                    FROM `1` AS `2`
                    ORDER BY `id` DESC)
                    SELECT `3`.`id` AS `id`, `3`.`22` AS `32`, `3`.`23` AS `33`
                    FROM `2` AS `3`
                    ORDER BY `id` DESC)
                    SELECT `4`.`id` AS `id`, `4`.`32` AS `42`, `4`.`33` AS `43`
                    FROM `3` AS `4`
                    ORDER BY `id` DESC
                    LIMIT 0, 10
                """.trimIndent()
            }
        }

        private fun assertSelect(r: Report = report, expected: () -> String) {
            assertEquals(expected().replace('\n', ' '), generator.buildSelect(r).toString())
        }
    }

    @Nested
    inner class SelectCountTest {

        @Test
        fun `base select`() {
            assertSelect { "SELECT COUNT(*) FROM `1` AS `2`" }
        }

        private fun assertSelect(expected: () -> String) {
            assertEquals(expected().replace("\n", ""), generator.buildSelectCount(report).toString())
        }
    }
}