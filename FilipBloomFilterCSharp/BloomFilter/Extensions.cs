using System;

namespace BloomFilter
{
    public static class Extensions
    {
        /// <summary>
        /// Left rotation.
        /// </summary>
        /// <returns>The left.</returns>
        /// <param name="value">Value.</param>
        /// <param name="count">Count.</param>
        public static uint RotateLeft(this uint value, int count)
        {
            return (value << count) | (value >> (32 - count));
        }

        /// <summary>
        /// Right rotation.
        /// </summary>
        /// <returns>The right.</returns>
        /// <param name="value">Value.</param>
        /// <param name="count">Count.</param>
        public static uint RotateRight(this uint value, int count)
        {
            return (value >> count) | (value << (32 - count));
        }

        /// <summary>
        /// Casts byte array to unsigned int.
        /// </summary>
        /// <returns>The uint32.</returns>
        /// <param name="bytes">Bytes.</param>
        public static uint ToUint32(this byte[] bytes)
        {
            return BitConverter.ToUInt32(bytes, 0);
        }

        /// <summary>
        /// Casts byte array to signed int.
        /// </summary>
        /// <returns>The int32.</returns>
        /// <param name="bytes">Bytes.</param>
        public static int ToInt32(this byte[] bytes)
        {
            return BitConverter.ToInt32(bytes, 0);
        }

        /// <summary>
        /// Used in Murmur.
        /// </summary>
        /// <returns>The mix.</returns>
        /// <param name="h">The height.</param>
        public static uint FMix(this uint h)
        {
            h = (h ^ (h >> 16)) * 0x85ebca6b;
            h = (h ^ (h >> 13)) * 0xc2b2ae35;
            return h ^ (h >> 16);
        }
    }
}

