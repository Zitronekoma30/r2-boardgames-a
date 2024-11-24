const { app, BrowserWindow } = require('electron');
const path = require('path');

function createWindow() {
  const mainWindow = new BrowserWindow({
    width: 1200,
    height: 800,
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: false,
    },
  });

  // Open DevTools
  mainWindow.webContents.openDevTools();

  /* Load the React app
  const startURL = process.env.NODE_ENV === 'production'
    ? `file://${path.join(__dirname, 'build', 'index.html')}`
    : 'http://localhost:3000';
  */

  mainWindow.loadURL(`file://${path.join(__dirname, '../chess-demo/src/view/index.html')}`);

  mainWindow.on('closed', () => {
    mainWindow = null;  // Dereference the window object
  });
}

// Quit the app when all windows are closed (except on macOS)
app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {  // 'darwin' is macOS
    app.quit();  // Close the Electron process
  }
});

app.whenReady().then(createWindow);

// Recreate a window in macOS when the dock icon is clicked
app.on('activate', () => {
  if (BrowserWindow.getAllWindows().length === 0) {
    createWindow();
  }
});