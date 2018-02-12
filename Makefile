run_all_in_parallel:
	make -j test_Android test_iOS
	
test_Android:
	platformName=Android mvn test

test_iOS:
	platformName=ios mvn test