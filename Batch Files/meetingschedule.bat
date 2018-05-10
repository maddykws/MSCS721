::author: Maddy
@echo off
call Creatordata.bat
::Declaring variable
 set /a "x = 1"
::Scheduling Meetings
:while1
    if %x% leq 3 (
	echo 4
    echo room%x%	
	echo 2018-03-%x%
	echo 12:%x%
	echo 2018-04-%x%
	echo 01:00
	echo subject%x%
	set /a "x = x + 1"	
	goto :while1
    )
set /a "x = 1"
:while2
    if %x% leq 3 (
	echo 3
	echo room%x%
	
	set /a "x = x + 1"	
		goto :while2
    )
	endlocal
	
	



