# PNG to PDF Batch Converter - VS Code Project

A ready-to-import Visual Studio Code project that converts 10 lakh (1,000,000) PNG images to PDF in batches of 5,010 images per PDF.

## 📁 Project Structure

```
png-to-pdf-converter/
├── .vscode/                          # VS Code configuration
│   ├── settings.json                 # Java & Maven settings
│   ├── launch.json                   # Run & Debug configurations
│   ├── tasks.json                    # Build tasks
│   └── extensions.json               # Recommended extensions
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── travelport/
│                   └── converter/
│                       └── PngToPdfBatchConverter.java
├── pom.xml                           # Maven dependencies
├── .gitignore                        # Git ignore rules
└── README.md                         # This file
```

## 🚀 Quick Start - Import to VS Code

### Step 1: Prerequisites

1. **Install Java JDK 8 or higher**
   - Download from: https://adoptium.net/
   - Verify installation: `java -version`

2. **Install Maven**
   - Download from: https://maven.apache.org/download.cgi
   - Add to PATH
   - Verify installation: `mvn -version`

3. **Install Visual Studio Code**
   - Download from: https://code.visualstudio.com/

### Step 2: Import the Project

1. **Extract the project folder** to your desired location
   
2. **Open in VS Code**:
   - Open VS Code
   - Click `File` → `Open Folder`
   - Select the `png-to-pdf-converter` folder
   - Click `Select Folder`

3. **Install Recommended Extensions** (VS Code will prompt you):
   - Extension Pack for Java
   - Maven for Java
   
   Or install manually:
   - Press `Ctrl+Shift+X` (Windows/Linux) or `Cmd+Shift+X` (Mac)
   - Search for "Extension Pack for Java"
   - Click Install

4. **Wait for Maven to sync**:
   - VS Code will automatically detect the `pom.xml` file
   - Maven will download dependencies (iText PDF library)
   - Wait for the progress indicator at the bottom to complete

### Step 3: Configure Your Paths

1. Open `PngToPdfBatchConverter.java`
2. Update the paths on lines 16-17:
   ```java
   private static final String SOURCE_DIR = "H:\\your\\path\\to\\png\\files";
   private static final String OUTPUT_DIR = "H:\\your\\path\\to\\output";
   ```

### Step 4: Build the Project

**Option A: Using VS Code Interface**
- Press `Ctrl+Shift+B` (Windows/Linux) or `Cmd+Shift+B` (Mac)
- Select `Maven: Clean and Package`

**Option B: Using Terminal**
- Press `` Ctrl+` `` to open terminal
- Run: `mvn clean package`

### Step 5: Run the Program

**Option A: Using Run Button (Recommended)**
- Open `PngToPdfBatchConverter.java`
- Look for the `Run` button above the `main` method
- Click `Run` or `Debug`

**Option B: Using Debug Panel**
- Press `Ctrl+Shift+D` (Windows/Linux) or `Cmd+Shift+D` (Mac)
- Select "Run PNG to PDF Converter" from dropdown
- Press F5 or click the green play button

**Option C: Using Built JAR**
- Open terminal in VS Code
- Run: `java -jar target/png-to-pdf-converter-1.0.0-jar-with-dependencies.jar`

## 📋 Features

### Batch Processing
- **Batch Size**: Exactly 5,010 images per PDF
- **Sequential Processing**: Each batch continues from where the previous ended
- **Batch Numbering**: Clear naming convention (Batch_001, Batch_002, etc.)

### Example Output
For 1,000,000 PNG images:
```
Batch_001_Images_1_to_5010.pdf
Batch_002_Images_5011_to_10020.pdf
Batch_003_Images_10021_to_15030.pdf
...
Batch_200_Images_996991_to_1000000.pdf
```

### Progress Tracking
```
Total PNG files found: 1000000
Batch size: 5010
Total batches to create: 200
Starting conversion...

Processing Batch 1 of 200
  Images: 1 to 5010
  Output: Batch_001_Images_1_to_5010.pdf
    Processed 500 images in current batch...
    Processed 1000 images in current batch...
  Status: Completed
```

## ⚙️ VS Code Features Configured

### 1. Debug Configurations
- **Run PNG to PDF Converter**: Normal execution
- **Debug PNG to PDF Converter**: Debug with breakpoints

### 2. Build Tasks (Ctrl+Shift+B)
- **Maven: Clean**: Clean build artifacts
- **Maven: Compile**: Compile source code
- **Maven: Package**: Build executable JAR (default)
- **Maven: Clean and Package**: Full rebuild
- **Run JAR**: Execute the built JAR file

### 3. IntelliSense & Auto-completion
- Full Java IntelliSense enabled
- Maven dependency auto-completion
- Code navigation (Go to Definition, Find References)

### 4. Error Detection
- Real-time syntax checking
- Maven dependency validation
- Import organization

## 🔧 Customization

### Change Batch Size
Edit line 15 in `PngToPdfBatchConverter.java`:
```java
private static final int BATCH_SIZE = 5010;  // Change to your desired size
```

### Increase Memory (for large images)
Edit `.vscode/launch.json` and add to configurations:
```json
"vmArgs": "-Xmx4G"
```

Or when running JAR manually:
```bash
java -Xmx4G -jar target/png-to-pdf-converter-1.0.0-jar-with-dependencies.jar
```

## 🐛 Troubleshooting

### "Maven not found" or "Java not found"
1. Ensure Java and Maven are installed
2. Restart VS Code after installation
3. Check PATH environment variables

### "Cannot resolve symbol" errors
1. Wait for Maven sync to complete (check bottom status bar)
2. Press `Ctrl+Shift+P` → type "Java: Clean Java Language Server Workspace"
3. Reload VS Code

### "No PNG files found"
1. Verify the SOURCE_DIR path is correct
2. Check that PNG files are directly in the folder (not subfolders)
3. Ensure path uses double backslashes (`\\`) on Windows

### Out of Memory Errors
1. Reduce BATCH_SIZE (try 2500 or 1000)
2. Increase heap size (see Customization section above)
3. Close other applications to free up memory

### Build fails
1. Run `mvn clean` first
2. Delete the `target` folder manually
3. Run `mvn package` again

## 📝 Keyboard Shortcuts

| Action | Windows/Linux | Mac |
|--------|---------------|-----|
| Run Code | F5 | F5 |
| Build Project | Ctrl+Shift+B | Cmd+Shift+B |
| Open Terminal | Ctrl+` | Ctrl+` |
| Show Debug Panel | Ctrl+Shift+D | Cmd+Shift+D |
| Command Palette | Ctrl+Shift+P | Cmd+Shift+P |

## 📦 Dependencies

The project uses:
- **iText PDF 5.5.13.3**: For PDF creation and manipulation
- Maven automatically downloads this when you open the project

## 🔐 Best Practices

1. **Test with small batch first**: 
   - Change BATCH_SIZE to 10
   - Test with 50-100 images first
   - Then scale up to 5010

2. **Monitor disk space**:
   - Each PDF will be several MB to GB depending on image sizes
   - Ensure adequate free space on output drive

3. **Backup your images**:
   - Keep original PNG files safe
   - The program doesn't modify source files

## 📊 Performance Notes

- **Processing Speed**: ~50-200 images per second (varies by hardware)
- **For 1,000,000 images**: Expect 1-6 hours depending on:
  - Image resolution
  - Hard drive speed (SSD recommended)
  - Available RAM
  - CPU performance

## 💡 Tips

1. **Run overnight**: For 10 lakh images, let it run overnight
2. **Use SSD**: Significantly faster than HDD
3. **Close other apps**: Free up memory for faster processing
4. **Check logs**: Monitor console output for any errors

## 📞 Support

If you encounter issues:
1. Check the console output in VS Code
2. Verify all paths are correct
3. Ensure you have sufficient disk space and memory
4. Try with a smaller batch size first

## 📄 License

Internal use only - Travelport

---

**Ready to start?** Just open the folder in VS Code and press F5! 🚀
