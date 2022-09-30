package com.ilyap.Addressing;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class IPv4 {
    private final String IPv4;
    private final int[] IPArray;
    private final int[] mask;
    private final long hosts;
    private final int[] networkAddress;
    private final int[] broadcastAddress;

    private final int changeableOctet;
    private final int offset;

    public IPv4(String IPAddress) {
        this.IPv4 = IPAddress;
        this.IPArray = parseIP(IPAddress);
        int prefixMask = IPArray[4];
        this.changeableOctet = 3 - (32 - prefixMask) / 8;
        this.offset = (int) Math.pow(2, (32 - prefixMask) % 8);

        this.hosts = (long) Math.pow(2, (32 - prefixMask));
        this.mask = parseMask();
        this.networkAddress = calcNetworkAddress();
        this.broadcastAddress = calcBroadcastAddress();
    }

    private int[] parseMask() {
        int[] fullMask = new int[]{255, 255, 255, 255};
        for (int i = fullMask.length - 1; i >= 0; i--) {
            if (i == changeableOctet) {
                fullMask[i] = 256 - offset;
                break;
            }
            fullMask[i] = 0;
        }
        return fullMask;
    }

    private int[] calcNetworkAddress() {
        int[] network = Arrays.copyOf(IPArray, 4);
        if (changeableOctet >= 0) {
            int subnetNum = IPArray[changeableOctet] / offset;
            int subnetOctet = subnetNum * offset;
            for (int i = network.length - 1; i >= 0; i--) {
                if (i == changeableOctet) {
                    network[i] = subnetOctet;
                    break;
                }
                network[i] = 0;
            }
        }
        return network;
    }

    private int[] calcBroadcastAddress() {
        int[] broadcast = networkAddress.clone();
        for (int i = broadcast.length - 1; i >= 0; i--) {
            if (i == changeableOctet) {
                broadcast[i] += offset - 1;
                break;
            }
            broadcast[i] = 255;
        }
        return broadcast;
    }

    public String getMask() {
        return convertToString(mask);
    }

    public String getHosts() {
        return String.valueOf(hosts);
    }

    public String getNetworkAddress() {
        return convertToString(networkAddress);
    }

    public String getBroadcastAddress() {
        return convertToString(broadcastAddress);
    }

    public String getIPv4() {
        return IPv4;
    }

    private String convertToString(int[] arr) {
        return Arrays.stream(arr)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining("."));
    }

    private int[] parseIP(String address) {
        String zeroTo255 = "(0{0,2}[0-9]|0?[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
        String mask = "([0-9]|[12][0-9]|3[012])";

        String regex = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "/"
                + mask;

        if (Pattern.matches(regex, address)) {
            String[] ipa = address.trim().split("[./]");
            return Arrays.stream(ipa)
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } else {
            throw new RuntimeException("Некорректный Addressing адрес!");
        }
    }
}