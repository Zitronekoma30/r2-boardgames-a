const { app, BrowserWindow } = require("electron");
const path = require("path");
const { exec } = require("child_process");

const PORT = 3000;
let mainWindow;
let isReactReady = false;

function createWindow() {
    if (mainWindow) return; // Prevent multiple windows
    mainWindow = new BrowserWindow({
        width: 800,
        height: 600,
        webPreferences: {
            nodeIntegration: false,
        },
    });

    mainWindow.loadURL(`http://localhost:${PORT}`);

    mainWindow.on("closed", () => (mainWindow = null));
}

function waitForReact() {
    const checkServer = setInterval(() => {
        require("http")
            .get(`http://localhost:${PORT}`, (res) => {
                if (res.statusCode === 200 && !isReactReady) {
                    clearInterval(checkServer);
                    isReactReady = true;
                    createWindow();
                }
            })
            .on("error", () => {
                console.log("Waiting for React to start...");
            });
    }, 1000);
}

function startReactApp() {
    console.log("Starting React app...");
    exec("npm start", (err, stdout, stderr) => {
        if (err) {
            console.error(`Error starting React app: ${err}`);
            return;
        }
        console.log(stdout);
    });

    waitForReact();
}

app.whenReady().then(startReactApp);

app.on("window-all-closed", () => {
    if (process.platform !== "darwin") app.quit();
});

app.on("activate", () => {
    if (!mainWindow) createWindow();
});
