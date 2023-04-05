sudo apt-get update
sudo apt-get install -y sysvbanner
banner connet
echo "Please click 'No' during macchanger installation, when it prompts for auto changing when interface is brought up"
sudo apt-get install -y git macchanger net-tools default-jdk default-jre
cd ~
mkdir connet
cd connet
git clone https://github.com/AdityaMitra5102/connet
javac *.java
cd ~/Desktop
echo cd ~/connet > connet.sh
echo sudo java Runner >> connet.sh
sudo chmod +x connet.sh
sudo ./connet.sh

