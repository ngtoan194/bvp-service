# bvp_test
echo "commit" + $1;
git add .;
echo "added";
git commit -am "$1";
git pull origin test --rebase
echo "commit"
git push origin test
curl -I -X POST http://admin:4cfc90785b667885ba48a1f2e58afa6d@jenkins.test.congdongyte.vn:8088/job/bvp_upgrade_service_test/build
