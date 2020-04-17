fun main() {
    print("Enter the filepath of the csv: ")
    val filePath: String = readLine()!!
    val parsedFilePath: String = "data/Parsed_Data.csv"
    writeToCSV(parsedFilePath, createNewCSV(readFromCSV(filePath)))
}