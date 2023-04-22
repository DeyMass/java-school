rm -f app/*.json

gradle run


echo "After run JSON users is:"
cat app/dev*.json | sed 's%},%,\n%g' ; echo 
echo "========================="
echo "After run JSON users is:"

cat app/man*.json | sed 's%},%,\n%g' ; echo 

