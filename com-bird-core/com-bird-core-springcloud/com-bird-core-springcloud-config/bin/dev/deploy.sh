./rebuild.sh
mkdir -p /opt/data/apps/apollo-cloud-system-config/lib
sudo rm -f /opt/data/apps/apollo-cloud-system-config/lib/*
sudo cp -f ../../release/lib/* /opt/data/apps/apollo-cloud-system-config/lib/

mkdir -p /opt/data/init.d/apollo-cloud-system-config
sudo rm -rf /opt/data/init.d/apollo-cloud-system-config/*
sudo cp -f ./shutdow* /opt/data/init.d/apollo-cloud-system-config
sudo cp -f ./start* /opt/data/init.d/apollo-cloud-system-config

mkdir -p /opt/data/conf/apollo-cloud-system-config
sudo rm -rf /opt/data/conf/apollo-cloud-system-config/*
sudo cp -f ../../resources/dev/* /opt/data/conf/apollo-cloud-system-config
sudo cp -f ../../resources/banner.txt /opt/data/conf/apollo-cloud-system-config
