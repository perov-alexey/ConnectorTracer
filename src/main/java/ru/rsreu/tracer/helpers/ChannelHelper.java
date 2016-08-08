package ru.rsreu.tracer.helpers;

import ru.rsreu.tracer.pojo.Channel;

public class ChannelHelper {

    public static boolean isChannelFull(Channel channel) {
        return channel.getOccupancy() == channel.getMaxCapacity();
    }
}
