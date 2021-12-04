package fendoudebb.fx.tool.netty.protocol;

import fendoudebb.fx.tool.netty.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

@Slf4j
public class MessageCodec extends ByteToMessageCodec<Message> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        // magic number: 4 byte
        out.writeBytes(new byte[]{(byte) 0xca, (byte) 0xfe, (byte) 0xba, (byte) 0xbe});
        // version: 1 byte
        out.writeByte(0xff);
        // serializer: 1 byte JDK:0 JSON:1 ...
        out.writeByte(0x00);
        // instruction type: 1 byte
        out.writeByte(msg.getMessageType());
        // sequence id: 4 byte
        out.writeInt(msg.getSequenceId());
        // padding byte: 1 byte meaningless
        out.writeByte(0x66);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(msg);
        byte[] bytes = bos.toByteArray();

        // payload length: 4 byte
        out.writeInt(bytes.length);
        // payload
        out.writeBytes(bytes);

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magicNum = in.readInt();
        byte version = in.readByte();
        byte serializer = in.readByte();
        byte messageType = in.readByte();
        int sequenceId = in.readInt();
        byte paddingByte = in.readByte();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        if (serializer == 0x00) {
            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            Message message = (Message) ois.readObject();
            log.info("decode#{},{},{},{},{},{},{},{}", magicNum, version, serializer, messageType, sequenceId, paddingByte, length, message);
            out.add(message);
        }
    }

    public static void main(String[] args) {
        System.out.println(0xf);
        System.out.println(0xff);
        System.out.println(0xfff);
        System.out.println(0x7fffffff);
        System.out.println(Integer.MAX_VALUE);
        System.out.println(Long.MAX_VALUE);
    }
}
