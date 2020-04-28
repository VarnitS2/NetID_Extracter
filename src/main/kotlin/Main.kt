fun mainMenu(): String {
    println("1. Parse a CSV file for NetIDs.")
    println("2. Compare a Google Form CSV with the master CSV.")
    println("3. Compare two Google Forms.")
    println("4. Exit.")
    return readLine()!!
}

fun main(args: Array<String>) {
    loop@ while (true) {
        when (mainMenu().toInt()) {
            1 -> parseCSV()
            2 -> missingRecords()
            3 -> missingRecords(true)
            4 -> break@loop
            else -> println("\nInvalid input.\n")
        }
    }
    println("\nExiting")
}