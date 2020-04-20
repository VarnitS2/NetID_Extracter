import ezgmail
from os import listdir
from os.path import isfile, join

if __name__ == '__main__':
    with open("googleFormDirectoryPath.txt") as f:
        googleFormDirectoryPath = f.readline()

    googleFormDirectoryList = [f for f in listdir(googleFormDirectoryPath) if isfile(join(googleFormDirectoryPath, f))]

    for file in googleFormDirectoryList:
        if file.startswith("Difference-Data-"):
            missingRecordCSVFilePath = googleFormDirectoryPath + "\\" + file

    with open(missingRecordCSVFilePath) as f:
        records = f.readlines()

    emails = []
    for record in records:
        emails.append(record.split(",")[1])

    print(emails[2])

    # ezgmail.send("varnits02@gmail.com", "CS 199 Test", "Ayo this a test baby")