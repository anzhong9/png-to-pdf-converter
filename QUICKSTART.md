# 🚀 QUICK START GUIDE

## For Visual Studio Code

### 1️⃣ Prerequisites (One-time setup)

**Install these if you don't have them:**

☐ **Java JDK 8+**
   - Download: https://adoptium.net/
   - Install and verify: Open CMD/Terminal → type `java -version`

☐ **Maven**
   - Download: https://maven.apache.org/download.cgi
   - Install and verify: Open CMD/Terminal → type `mvn -version`

☐ **Visual Studio Code**
   - Download: https://code.visualstudio.com/
   - Install normally

### 2️⃣ Import Project (2 minutes)

1. **Extract** the `png-to-pdf-converter` folder to your desktop or any location

2. **Open VS Code**
   - File → Open Folder
   - Select the `png-to-pdf-converter` folder
   - Click "Select Folder"

3. **Install Extensions** (VS Code will ask you)
   - Click "Install" when prompted for recommended extensions
   - OR manually install "Extension Pack for Java"

4. **Wait for setup** (1-2 minutes)
   - VS Code will download Java libraries automatically
   - Look for progress indicator at bottom-right
   - Wait until it says "Ready"

### 3️⃣ Configure Your Paths (30 seconds)

1. In VS Code, open: `src/main/java/com/travelport/converter/PngToPdfBatchConverter.java`

2. Find lines 16-17 and update YOUR paths:
   ```java
   private static final String SOURCE_DIR = "H:\\YOUR\\PNG\\FOLDER\\PATH";
   private static final String OUTPUT_DIR = "H:\\YOUR\\OUTPUT\\FOLDER\\PATH";
   ```

3. Save the file (Ctrl+S)

### 4️⃣ Build Project (1 minute)

**Option A - Using Keyboard:**
- Press `Ctrl+Shift+B` (Windows/Linux) or `Cmd+Shift+B` (Mac)
- Select "Maven: Clean and Package"
- Wait for "BUILD SUCCESS" message

**Option B - Using Terminal:**
- Press `` Ctrl+` `` to open terminal
- Type: `mvn clean package`
- Press Enter
- Wait for "BUILD SUCCESS"

### 5️⃣ Run the Program

**Easiest Way:**
1. Open `PngToPdfBatchConverter.java` file
2. Look for "Run | Debug" text above `public static void main`
3. Click "Run"
4. Watch the progress in the terminal below!

**Alternative - Using F5:**
1. Press `F5` on keyboard
2. Program starts automatically

### 6️⃣ Check Your Output

- Go to your OUTPUT_DIR folder
- You'll see PDFs named:
  - `Batch_001_Images_1_to_5010.pdf`
  - `Batch_002_Images_5011_to_10020.pdf`
  - etc.

---

## ⚡ That's It! You're Done!

### What happens when you run:
```
✓ Reads all PNG files from your SOURCE_DIR
✓ Sorts them alphabetically
✓ Creates PDFs with exactly 5,010 images each
✓ Each batch starts where the previous ended
✓ Shows progress every 500 images
✓ Saves PDFs to your OUTPUT_DIR
```

### For 1,000,000 images:
- Creates ~200 PDF files
- Takes 1-6 hours depending on your computer
- You can leave it running in the background

---

## 🆘 Having Issues?

### "Maven not found" or "Java not found"
→ Restart VS Code after installing Java/Maven

### Red squiggly lines in code
→ Wait 2 minutes for Maven to finish downloading libraries

### "No PNG files found"
→ Check your SOURCE_DIR path (use double backslashes `\\` on Windows)

### Need more help?
→ Check the full README.md file in the project folder

---

## 💡 Pro Tips

1. **Test First**: Before processing 10 lakh images, try with 100 images first
   - Change `BATCH_SIZE` to 10
   - Use a small test folder

2. **Monitor Progress**: Watch the VS Code terminal - it shows real-time progress

3. **Free Up Memory**: Close other apps while processing large batches

4. **Use SSD**: If possible, run from SSD drive (much faster than HDD)

---

**Questions? Check README.md for detailed information!**
