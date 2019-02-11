# bvp_test
echo "commit" + $1;
git add .;
echo "added";
git ci -am "$1";
echo "commit"
git push origin test
