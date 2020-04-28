import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import java.io.File
import java.time.LocalDateTime

fun createDateTimeString(): String {
    val today = LocalDateTime.now().toString().split("T")[0]
    val hour = LocalDateTime.now().hour
    val minute = LocalDateTime.now().minute
    return "$today-$hour-$minute"
}

fun readFromCSVWithHeader(fileName: String): List<Map<String, String>> {
    return csvReader().readAllWithHeader(File(fileName))
}

fun readFromCSV(fileName: String): List<List<String>> {
    return csvReader().readAll(File(fileName))
}

fun writeToCSV(fileName: String, rows: List<List<String>>) {
    csvWriter().open(fileName) { writeAll(rows) }
}

// TODO: Account for CSVs with different header names.
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
    print("\nEnter the filepath of the CSV: ")
    val filePath: String = readLine()!!
    val parsedFilePath = "data/discourse_csv/Parsed-Data-${createDateTimeString()}.csv"

    writeToCSV(parsedFilePath, createNewCSV(readFromCSVWithHeader(filePath)))
    println("The parsed file is saved at $parsedFilePath\n")
}

fun getFilePath(fileType: String): String {
    if (fileType == "masterCSV") {
        File("data/discourse_csv/").walk().forEach {
            if (it.toString().startsWith("data\\discourse_csv\\Parsed-Data-")) {
                return it.toString()
            }
        }
    }

    return ""
}

fun missingRecords(googleForm: Boolean = false) {
    if (googleForm) {
        print("\nEnter the filepath of the first Google Form CSV: ")
    } else {
        print("\nEnter the filepath of the CSV with all student records: ")
    }
    val masterFilePath: String = readLine()!!

    print("Enter the filepath of the Google Form CSV: ")
    val filePath: String = readLine()!!

    val parsedFilePath = getFilePath("masterCSV")

    val masterList = readFromCSVWithHeader(masterFilePath)
    val otherList = readFromCSVWithHeader(filePath)
    val parsedList = readFromCSVWithHeader(parsedFilePath)

    val masterEmailList = mutableListOf<String>()
    val otherEmailList = mutableListOf<String>()

    for (record in masterList) {
        if (googleForm) {
            masterEmailList.add(record["Username"].toString())
        } else {
            masterEmailList.add(record["email"].toString())
        }
    }
    for (record in otherList) {
        otherEmailList.add(record["Username"].toString())
    }

    val emailDifference = masterEmailList.toSet().minus(otherEmailList.toSet()).toList()
    val nameDifference = mutableListOf<String>()
    val netIDDifference = mutableListOf<String>()

    for (email in emailDifference) {
        if (googleForm) {
            for (record in parsedList) {
                if (email == record["email"]) {
                    nameDifference.add(record["name"].toString())
                    netIDDifference.add(record["netid"].toString())
                }
            }
        } else {
            for (record in masterList) {
                if (email == record["email"]) {
                    nameDifference.add(record["name"].toString())
                    netIDDifference.add(record["netid"].toString())
                }
            }
        }
    }

    val difference = mutableListOf<List<String>>()
    difference.add(listOf("name", "email", "netid"))

    for (i in emailDifference.indices) {
        difference.add(listOf(nameDifference[i], emailDifference[i], netIDDifference[i]))
    }

    val differenceFilePath = "data/google_form_csv/Difference-Data-${createDateTimeString()}.csv"
    writeToCSV(differenceFilePath, difference)
    println("The parsed file is saved at $differenceFilePath\n")
}