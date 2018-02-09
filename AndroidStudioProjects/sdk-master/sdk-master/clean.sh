#!/bin/sh
##
# @file clean.sh
# @brief removes all temporary, generated files
#
# (c) 2013-2014 by Mega Limited, Wellsford, New Zealand
#
# This file is part of the MEGA SDK - Client Access Engine.
#
# Applications using the MEGA API must present a valid application key
# and comply with the the rules set forth in the Terms of Service.
#
# The MEGA SDK is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
#
# @copyright Simplified (2-clause) BSD License.
#
# You should have received a copy of the license along with this
# program.
##
TARGETS="aclocal.m4 autom4te.cache config.guess config.log config.status config.sub configure depcomp install-sh libmega.pc libtool ltmain.sh Makefile Makefile.in missing stamp-h1 test-driver texput.log .deps clean compile
examples/.deps examples/.dirstamp examples/linux/.deps examples/linux/.dirstamp examples/linux/.libs examples/linux/megafuse examples/linux/*.o examples/*.o
examples/megacli examples/megasimplesync examples/.libs
include/Makefile include/Makefile.in
include/mega/config.h include/mega/config.h.in include/mega/stamp-h1
m4/libtool.m4 m4/lt~obsolete.m4  m4/ltoptions.m4  m4/ltsugar.m4  m4/ltversion.m4
src/*.lo
src/.libs
src/libmega.la
src/thread/.deps src/thread/.dirstamp src/thread/.libs src/thread/*.lo
src/.deps src/.dirstamp
src/crypto/.deps src/crypto/.dirstamp src/crypto/.libs src/crypto/*.lo
src/db/.deps src/db/.dirstamp src/db/.libs src/db/*.lo
src/gfx/.deps src/gfx/.dirstamp src/gfx/.libs src/gfx/*.lo
src/posix/.deps src/posix/.dirstamp src/posix/.libs src/posix/*.lo
src/win32/.deps src/win32/.dirstamp
tests/.deps tests/.dirstamp
doc/api doc/sphinx_api doc/_build
megacli1 megacli2 sync_in sync_out out
sdk_build
"
for file in $TARGETS
do
    rm -fr $file
done
