::author: Maddy
::Import from JSON data
@ECHO OFF
FOR /F "tokens=*" %%i IN (file1.json) DO @ECHO %%i

 set /a x =2
 set /a z=12
 set /a y=10
 set /a m=10
 set /a minutes=20
 set /a hours =56
 
 echo [

:while1
    if %x% leq 121 (
	set /a "x = x + 1"
    set /a y = y+1	
	set /a z=z+1
	set /a minutes+=1
	set /a hours+=1	
	if %y% ==30 set y=10
	set /a m=m+1
	if %m% ==12 set m=1	
	if %z% ==30 set z=10
	if %minutes% ==60 set minutes=10
if %hours% ==24 set hours=10	
call :sub >100rooms
     :sub
	
	goto :while1
	
    )
	
	echo )]
