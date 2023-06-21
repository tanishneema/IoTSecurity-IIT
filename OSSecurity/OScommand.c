#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifdef _WIN32
#include <windows.h>
#else
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <dirent.h>
#endif

int isCommandAllowed(const char *command)
{
    const char *allowedCommands[] = {
        "dir", "echo", "type", "mkdir", "notepad", "calc"};
    
    int numAllowedCommands = sizeof(allowedCommands) / sizeof(allowedCommands[0]);

    for (int i = 0; i < numAllowedCommands; i++)
    {
        if (strcmp(command, allowedCommands[i]) == 0)
        {
            return 1; // Command is allowed
        }
    }

    return 0; // Command is disallowed
}

#ifdef _WIN32

void createDirectory(const char *dirname)
{
    if (!CreateDirectory(dirname, NULL))
    {
        printf("Failed to create directory!\n");
    }
    else
    {
        printf("Directory created successfully!\n");
    }
}

void listDirectory(const char *dirname)
{
    WIN32_FIND_DATA findData;
    HANDLE hFind;

    char searchPath[MAX_PATH];
    sprintf(searchPath, "%s\\*", dirname);

    hFind = FindFirstFile(searchPath, &findData);
    if (hFind == INVALID_HANDLE_VALUE)
    {
        printf("Failed to list directory contents!\n");
    }
    else
    {
        printf("Directory contents:\n");
        do
        {
            if (!(findData.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY))
            {
                printf("%s\n", findData.cFileName);
            }
        } while (FindNextFile(hFind, &findData) != 0);
        FindClose(hFind);
    }
}
#endif

int main()
{
    char userCommand[100];
    printf("Enter a command: ");
    fgets(userCommand, sizeof(userCommand), stdin);
    userCommand[strcspn(userCommand, "\n\r")] = '\0';

    if (strpbrk(userCommand, "|&;<>()$`\\\"'\t\n\r") != NULL)
    {
        printf("Invalid command!\n");
        return 1;
    }

#ifdef _WIN32

    if (!isCommandAllowed(userCommand))
    {
        printf("Invalid command!\n");
        return 1;
    }

    if (strcmp(userCommand, "notepad") == 0)
    {
        system("start notepad.exe");
    }
    else if (strcmp(userCommand, "calc") == 0)
    {
        system("calc");
    }
    else if (strcmp(userCommand, "dir") == 0)
    {
        listDirectory(".");
    }
    else if (strncmp(userCommand, "mkdir ", 6) == 0)
    {
        createDirectory(userCommand + 6);
    }
    else
    {
        system(userCommand);
    }
#endif

    return 0;
}