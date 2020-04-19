import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.time.LocalDateTime

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

fun parseCSV() {
    val today = LocalDateTime.now().toString().split("T")[0]
    val hour = LocalDateTime.now().hour
    val minute = LocalDateTime.now().minute

    print("\nEnter the filepath of the csv: ")
    val filePath: String = readLine()!!
    val parsedFilePath = "data/Parsed-Data-$today-$hour-$minute.csv"

    writeToCSV(parsedFilePath, createNewCSV(readFromCSV(filePath)))
    println("The parsed file is saved at $parsedFilePath\n")
}