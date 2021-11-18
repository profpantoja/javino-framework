# Script de instalação de ambiente para o Ubuntu 20.04.03 LTS
#
cd ~

sudo apt update

sudo apt install python3-serial

wget -c --show-progress https://turing.pro.br/kadupantoja/t00ls/javino/ubuntu/jdk-11.0.12_linux-x64_bin.deb -O /tmp/jdk.deb
sudo dpkg -i /tmp/jdk.deb

cat /etc/environment | grep -v "PATH" > /tmp/environment
echo -n "PATH=\"" >> /tmp/environment
echo -n `cat /etc/environment | cut -d '"' -f 2` >> /tmp/environment
echo ":/usr/lib/jvm/jdk-11.0.12/bin/\"" >> /tmp/environment
echo JAVA_HOME=\"/usr/lib/jvm/jdk-11.0.12/\" >> /tmp/environment
sudo mv /etc/environment /etc/environment.backup
sudo mv /tmp/environment /etc/environment

wget -c --show-progress https://turing.pro.br/kadupantoja/t00ls/javino/ubuntu/eclipse-java-2021-09-R-linux-gtk-x86_64.tar.gz -O /tmp/eclipse.tar.gz
tar -zxvf /tmp/eclipse.tar.gz


wget -c --show-progress https://turing.pro.br/kadupantoja/t00ls/javino/ubuntu/arduino-1.8.16-linux64.tar.xz -O /tmp/arduino.tar.xz
tar -xf /tmp/arduino.tar.xz
sudo ~/arduino-1.8.16/arduino-linux-setup.sh 






