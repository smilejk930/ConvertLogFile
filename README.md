--
@Release 2019-10-09
@author SMILEJK
--

1. ConvertLogFile.exe
    : *.bl 파일을 *.json 파일로 변환해주는 JAVA 기반의 프로그램    

2. DecodeLogFile.exe
    : *.bl 파일을 *.xml 파일로 변환해주는 프로그램
    : ConvertLogFile.exe 프로그램과의 연동으로 따로 실행시키지 않아도 됨.

3. 만들어지는 파일
    : *.xml (*.bl 파일이 있는 곳에 자동적으로 생성)
    : *.json (json 형식)

4. jre8 폴더와 ConvertLogFile.exe 파일이 같은 경로 내에 존재해야 작동 함.
    : 예제) ..\ConvertLogFile\ConvertLogFile.exe
    : 예제) ..\ConvertLogFile\jre8

5. ConvertLogFile.ini
    : 환경 설정 셋팅 파일

6. 프로그램 설명
    1) DecodeLogFile.exe 경로 버튼: DecodeLogFile.exe 파일이 존재하는 경로 선택
    2) 저장 경로 버튼: 변환 되어진 *.json 파일이 저장될 경로 선택
    3) 파일 열기 버튼: 변환 해야하는 *.bl 파일 선택
    4) 변 환 버튼: 변환 시작
