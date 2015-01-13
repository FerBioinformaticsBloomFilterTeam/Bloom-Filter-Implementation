using System;

namespace BloomFilter
{
    public static class Extensions
    {
        public static uint RotateLeft(this uint value, int count)
        {
            return (value << count) | (value >> (32 - count));
        }

        public static uint RotateRight(this uint value, int count)
        {
            return (value >> count) | (value << (32 - count));
        }

        public static uint ToUint32(this byte[] bytes)
        {
            return BitConverter.ToUInt32(bytes, 0);
        }

        public static int ToInt32(this byte[] bytes)
        {
            return BitConverter.ToInt32(bytes, 0);
        }

        public static uint FMix(this uint h)
        {
            h = (h ^ (h >> 16)) * 0x85ebca6b;
            h = (h ^ (h >> 13)) * 0xc2b2ae35;
            return h ^ (h >> 16);
        }
    }
}

