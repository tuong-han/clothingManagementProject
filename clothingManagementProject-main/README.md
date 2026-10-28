# Clothing Management Project

## Run

The project targets Java 8 or newer. From the project root in PowerShell:

```powershell
$sources = Get-ChildItem -Path 'src' -Recurse -Filter '*.java' | ForEach-Object { $_.FullName }
javac -encoding UTF-8 -source 1.8 -target 1.8 -d 'build\classes' $sources
java -cp 'build\classes' ui.Main
```

The main menu provides customer and product management, sales, and sales reports.
Customer data is stored in `customers.txt` and product/stock data in
`products.txt`, relative to the directory where the program is launched.
Completed orders and reports remain in memory for the current run.