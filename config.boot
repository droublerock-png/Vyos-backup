interfaces {
    ethernet eth0 {
        hw-id "00:0c:29:c0:0e:6c"
    }
    ethernet eth1 {
        hw-id "00:0c:29:c0:0e:76"
    }
    ethernet eth2 {
        address "172.30.255.1/30"
        description "OPN_TRANSIT"
        hw-id "00:0c:29:c0:0e:80"
    }
    loopback lo {
    }
    pppoe pppoe0 {
        authentication {
            password "2982646s"
            username "c-2982646"
        }
        description "WAN_Sarkor_PPPoE"
        mtu "1492"
        source-interface "eth1"
    }
}
load-balancing {
    wan {
        flush-connections
        interface-health eth0 {
            nexthop "194.93.24.81"
            test 1 {
                target "8.8.8.8"
                type "ping"
            }
        }
        interface-health pppoe0 {
            nexthop "dhcp"
            test 1 {
                target "1.1.1.1"
                type "ping"
            }
        }
        rule 1 {
            failover
            inbound-interface "eth2"
            interface eth0 {
                weight "100"
            }
            interface pppoe0 {
                weight "1"
            }
        }
    }
}
nat {
    destination {
        rule 10 {
            description "HTTPS to OPNsense"
            destination {
                address "82.215.69.184"
                port "443"
            }
            protocol "tcp"
            translation {
                address "172.30.255.2"
            }
        }
        rule 11 {
            description "HTTPS to OPNsense via UzOnline"
            destination {
                address "194.93.24.92"
                port "443"
            }
            protocol "tcp"
            translation {
                address "172.30.255.2"
            }
        }
        rule 20 {
            description "OpenVPN to OPNsense"
            destination {
                address "82.215.69.184"
                port "1194"
            }
            protocol "udp"
            translation {
                address "172.30.255.2"
            }
        }
        rule 21 {
            description "OpenVPN to OPNsense via UzOnline"
            destination {
                address "194.93.24.92"
                port "1194"
            }
            protocol "udp"
            translation {
                address "172.30.255.2"
            }
        }
        rule 22 {
            description "VPN-HR to OPNsense"
            destination {
                address "82.215.69.184"
                port "1195"
            }
            protocol "udp"
            translation {
                address "172.30.255.2"
                port "1195"
            }
        }
        rule 23 {
            description "VPN-SALES to OPNsense"
            destination {
                address "82.215.69.184"
                port "1196"
            }
            protocol "udp"
            translation {
                address "172.30.255.2"
                port "1196"
            }
        }
        rule 24 {
            description "VPN-IT to OPNsense"
            destination {
                address "82.215.69.184"
                port "1197"
            }
            protocol "udp"
            translation {
                address "172.30.255.2"
                port "1197"
            }
        }
        rule 25 {
            description "VPN-ANALYTICS to OPNsense"
            destination {
                address "82.215.69.184"
                port "1200"
            }
            protocol "udp"
            translation {
                address "172.30.255.2"
                port "1200"
            }
        }
        rule 26 {
            description "VPN-BUX to OPNsense"
            destination {
                address "82.215.69.184"
                port "1202"
            }
            protocol "udp"
            translation {
                address "172.30.255.2"
                port "1202"
            }
        }
        rule 27 {
            description "VPN-GREEN to OPNsense"
            destination {
                address "82.215.69.184"
                port "1203"
            }
            protocol "udp"
            translation {
                address "172.30.255.2"
                port "1203"
            }
        }
    }
    source {
        rule 100 {
            outbound-interface {
                name "pppoe0"
            }
            source {
                address "172.16.0.0/12"
            }
            translation {
                address "masquerade"
            }
        }
    }
}
protocols {
    static {
        route 0.0.0.0/0 {
            interface pppoe0 {
            }
        }
        route 172.16.0.0/25 {
            next-hop 172.30.255.2 {
            }
        }
    }
}
service {
    ntp {
        allow-client {
            address "0.0.0.0/0"
            address "::/0"
        }
        server time1.vyos.net {
        }
        server time2.vyos.net {
        }
        server time3.vyos.net {
        }
    }
    ssh {
        listen-address "0.0.0.0"
        port "22"
    }
}
system {
    config-management {
        commit-revisions "100"
    }
    conntrack {
        modules {
            ftp
            h323
            nfs
            pptp
            sip
            sqlnet
            tftp
        }
    }
    console {
        device ttyS0 {
            speed "115200"
        }
    }
    host-name "vyos"
    login {
        user vyos {
            authentication {
                encrypted-password "$6$rounds=656000$NiyRrRnL.5ylpnAw$9M/3MPhD07tXC5l2yH2wOwWdHg/BTN/gCFxPJRsHWPbsyrlrlCWWwMNqD2y7ZLOj/uRatxHvxLwETuLqGe6Ai1"
                plaintext-password ""
            }
        }
    }
    name-server "8.8.8.8"
    name-server "1.1.1.1"
    syslog {
        global {
            facility all {
                level "info"
            }
            facility local7 {
                level "debug"
            }
        }
    }
}


// Warning: Do not remove the following line.
// vyos-config-version: "bgp@4:broadcast-relay@1:cluster@2:config-management@1:conntrack@3:conntrack-sync@2:container@1:dhcp-relay@2:dhcp-server@6:dhcpv6-server@1:dns-dynamic@3:dns-forwarding@4:firewall@13:flow-accounting@1:https@5:ids@1:interfaces@32:ipoe-server@1:ipsec@12:isis@3:l2tp@4:lldp@1:mdns@1:monitoring@1:nat@7:nat66@3:ntp@3:openconnect@2:ospf@2:pim@1:policy@7:pppoe-server@6:pptp@2:qos@2:quagga@11:rip@1:rpki@1:salt@1:snmp@3:ssh@2:sstp@4:system@26:vrf@3:vrrp@4:vyos-accel-ppp@2:wanload