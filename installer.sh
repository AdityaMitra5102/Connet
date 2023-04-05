sudo apt-get update
sudo apt-get install -y sysvbanner git macchanger net-tools default-jdk default-jre
banner connet
echo "Please click 'No' during macchanger installation, when it prompts for auto changing when interface is brought up"
cd ~
git clone https://github.com/AdityaMitra5102/connet
sudo chmod -R 777 connet
cd connet
javac *.java
cd ~/Desktop
echo cd ~/connet > connet.sh
echo sudo java Runner >> connet.sh
sudo chmod +x connet.sh
clear
echo Shortcut created on Desktop
banner connet
