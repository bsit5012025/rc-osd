# The Development of Prefect Management System for the Office of Student Discipline (RC-OSD)

# Description:
The Prefect Management System for the Office of Student Discipline (RC-OSD) is a desktop-based application developed to support the administrative operations of the Office of Student Discipline. The system provides prefects with an efficient platform to manage and monitor student disciplinary cases in a structured and organized manner.

The application enables prefects to record student violations, review and process appeals, manage permission requests, and monitor disciplinary activities through a centralized dashboard. By digitizing these processes, the system reduces reliance on manual record-keeping, improves accuracy, and enhances the efficiency of disciplinary case management.

# Features: 

Dashboard: Provides an overview of violations, pending appeals, and submitted requests.

Violation Recording: Allows prefects to document and manage student offenses.

Appeals Management: Enables review and processing of student appeals.

Permissions Handling: Allows prefects to manage and track permission requests.

User Authentication: Secure login and logout functionality for authorized prefects.

# Technologies Used: 
- Java
- Java Swing (Desktop UI)
- MVC Architecture
- DAO Design Pattern
- Oracle Database
- WiX Toolset v3.11.1 (Installer)

# Installation 
1. Open the WiX Toolset releases page: Releases · wixtoolset/wix3 and locate WiX Toolset v3.11.1.

2. Open the Assets section of WiX Toolset v3.11.1, then click wix311.exe to download the installer.

3. After the download is complete, open your Downloads folder and double-click wix311.exe to run the installer.

4. If an error appears stating that .NET Framework 3.5 is required, enable it using the following steps:

- Press the Windows key and search for PowerShell.

- Right-click PowerShell and select Run as Administrator.

- Confirm the administrator prompt.

- Run the following command:  Enable-WindowsOptionalFeature -Online -FeatureName "NetFx3" -All

5. Press Enter and wait for the installation process to complete. Loading indicators may appear while Windows installs the required components.

6. When prompted to restart the computer, type Y and press Enter to restart the system and apply the changes.

7. After the system restarts, run wix311.exe again if necessary and proceed with the installation.

8. Follow the installation wizard and click Install to install WiX Toolset.

9. After installation is complete, navigate to the WiX Toolset installation directory, usually located at: C:\Program Files (x86)\WiX Toolset v3.11

10. Open the WiX Toolset v3.11 folder and then open the bin folder.

11. Copy the full path of the bin folder.

12. Add the copied path to the system PATH environment variable:
- Press the Windows key and search for Environment Variables

- Select Edit the system environment variables.
  
- Click Environment Variables.

- Under System Variables, select Path and click Edit.

- Click New, paste the copied path, and press OK to save the changes.

# System Requirements
- Windows 10 or higher
- Minimum 4 GB RAM

# Contributors
- Donnie Anciro 
- Wilrow Bayona
- Mark Joshua Camama
- John Zenith Cruz
- Angel Lowyza De Gala
- Geoffrey Allen De Rojas
- Keith Jasper Quevada
- Leeane Glazel Reyes



