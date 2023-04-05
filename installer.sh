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
cd ~/connet
sudo dpkg -i windscribe.deb
clear
banner connet
echo Shortcut created on Desktop
echo Run the command 'windscribe login' to set up VPN
echo Create a VPN account on windscribe.com and enter your credentials here
