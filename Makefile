run_all_in_parallel:
	make -j test_Windows8.1_firefox_40 test_Windows7_chrome_45
	
test_Windows8.1_firefox_40:
	browserName=firefox version=40.0 platform="Windows 8.1" mvn test

test_Windows7_chrome_45:
	browserName=chrome version=45.0 platform="Windows 7" mvn test