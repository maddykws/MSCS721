::author:Maddy
@echo off
set /a "x = 1"
:while1
    if %x% leq 2 (
	 echo 1
    echo room%x%
    echo 10
    echo Marist%x%
    echo LT%x%
    set /a "x = x + 1"		    
    echo room%x% added!
		goto :while1
    )
endlocal

echo 4
