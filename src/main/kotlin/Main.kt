fun mainMenu(): String {
    println("1. Parse a CSV file for NetIDs.")
    println("2. Find missing records from a CSV.")
    println("3. Exit.")
    return readLine()!!
}

fun main(args: Array<String>) {
    loop@ while (true) {
        when (mainMenu().toInt()) {
            1 -> parseCSV()
            2 -> missingRecords()
            3 -> break@loop
            else -> println("\nInvalid input.\n")
        }
    }
    println("\nExiting")
}