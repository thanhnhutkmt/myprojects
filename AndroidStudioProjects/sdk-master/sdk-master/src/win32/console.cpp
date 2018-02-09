/**
 * @file win32/console.cpp
 * @brief Win32 console I/O
 *
 * (c) 2013-2014 by Mega Limited, Wellsford, New Zealand
 *
 * This file is part of the MEGA SDK - Client Access Engine.
 *
 * Applications using the MEGA API must present a valid application key
 * and comply with the the rules set forth in the Terms of Service.
 *
 * The MEGA SDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * @copyright Simplified (2-clause) BSD License.
 *
 * You should have received a copy of the license along with this
 * program.
 */

#include "mega.h"
#include <windows.h>
#include <conio.h>

#include <io.h>
#include <fcntl.h>

namespace mega {
WinConsole::WinConsole()
{
    // FIXME: configure for UTF-8 I/O
}

WinConsole::~WinConsole()
{
    // restore startup config
}

void WinConsole::readpwchar(char* pw_buf, int pw_buf_size, int* pw_buf_pos, char** line)
{
    char c;
    DWORD cread;

    if (ReadConsole(GetStdHandle(STD_INPUT_HANDLE), &c, 1, &cread, NULL) == 1)
    {
        if ((c == 8) && *pw_buf_pos)
        {
            (*pw_buf_pos)--;
        }
        else if (c == 13)
        {
            *line = (char*)malloc(*pw_buf_pos + 1);
            memcpy(*line, pw_buf, *pw_buf_pos);
            (*line)[*pw_buf_pos] = 0;
        }
        else if (*pw_buf_pos < pw_buf_size)
        {
            pw_buf[(*pw_buf_pos)++] = c;
        }
    }
}

void WinConsole::setecho(bool echo)
{
    HANDLE hCon = GetStdHandle(STD_INPUT_HANDLE);
    DWORD mode;

    GetConsoleMode(hCon, &mode);

    if (echo)
    {
        mode |= ENABLE_ECHO_INPUT | ENABLE_LINE_INPUT;
    }
    else
    {
        mode &= ~(ENABLE_ECHO_INPUT | ENABLE_LINE_INPUT);
    }

    SetConsoleMode(hCon, mode);
}
} // namespace
