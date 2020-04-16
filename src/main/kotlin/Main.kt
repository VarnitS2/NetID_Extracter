import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File

fun readFromCSV(fileName: String): List<Map<String, String>> {
    return csvReader().readAllWithHeader(File(fileName))
}

fun writeToCSV(fileName: String, rows: List<List<String>>) {
    csvWriter().open(fileName) { writeAll(rows) }
}

fun createNewCSV(rows: List<Map<String, String>>): List<List<String>> {
    val newRows = mutableListOf<List<String>>()
    newRows.add(listOf("name", "email", "netid"))

    for (row in rows) {
        if (!row["admin"]?.toBoolean()!!) {
            val name: String = row["name"].toString()
            val email: String = row["email"].toString()
            val netID: String = email.split("@")[0]
            newRows.add(listOf(name, email, netID))
        }
    }

    return newRows
}

fun main() {
    writeToCSV("data.csv", createNewCSV(readFromCSV("user-list-200412-142909-1.csv")))
}