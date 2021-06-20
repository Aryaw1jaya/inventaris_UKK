-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 13, 2020 at 03:42 AM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.2.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `inventaris_ukk`
--

-- --------------------------------------------------------

--
-- Table structure for table `denda`
--

CREATE TABLE `denda` (
  `id_denda` varchar(10) NOT NULL,
  `id_pengembalian` varchar(10) NOT NULL,
  `keterangan` varchar(200) DEFAULT NULL,
  `jenis` tinyint(1) NOT NULL,
  `denda` int(9) NOT NULL,
  `tgl_bayar` date DEFAULT NULL,
  `id_petugas` varchar(10) DEFAULT NULL,
  `status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `denda`
--

INSERT INTO `denda` (`id_denda`, `id_pengembalian`, `keterangan`, `jenis`, `denda`, `tgl_bayar`, `id_petugas`, `status`) VALUES
('20205342', 'Sat5342', NULL, 1, 300000, NULL, NULL, 1),
('20205346', 'Sat5346', NULL, 1, 300000, NULL, NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `detail_inv`
--

CREATE TABLE `detail_inv` (
  `id_detail` int(11) NOT NULL,
  `id_barang` varchar(10) NOT NULL,
  `merk` varchar(15) NOT NULL,
  `ketersediaan` tinyint(1) NOT NULL,
  `keterangan` varchar(200) NOT NULL,
  `tgl_masuk` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detail_inv`
--

INSERT INTO `detail_inv` (`id_detail`, `id_barang`, `merk`, `ketersediaan`, `keterangan`, `tgl_masuk`) VALUES
(1, 'LAP', 'Asus', 0, '', '2020-02-07'),
(1, 'FLA', 'sandstone', 0, 'Habis', '2020-02-07'),
(1, 'MOU', 'logitech', 0, 'Habis', '2020-02-07'),
(1, 'KEY', 'logitech', 0, 'Habis', '2020-02-07'),
(2, 'KEY', 'logitech', 1, '', '2020-02-07'),
(1, 'BAT', 'Abc', 0, 'Habis', '2020-02-07'),
(2, 'MOU', 'msi', 1, '', '2020-02-07'),
(3, 'MOU', 'msi', 1, '', '2020-02-07'),
(4, 'MOU', 'msi', 0, '', '2020-02-07'),
(1, 'CDR', 'samsung', 0, 'Habis', '2020-02-07'),
(1, 'SEP', 'Adidas', 1, 'Habis', '2020-02-07'),
(2, 'SEP', 'Adidas', 1, '', '2020-02-07'),
(3, 'SEP', 'Adidas', 1, '', '2020-02-07'),
(4, 'SEP', 'Adidas', 1, '', '2020-02-07'),
(5, 'SEP', 'Adidas', 1, '', '2020-02-07'),
(6, 'SEP', 'Adidas', 1, '', '2020-02-07'),
(7, 'SEP', 'Adidas', 1, '', '2020-02-07'),
(8, 'SEP', 'Nike', 1, '', '2020-02-08'),
(9, 'SEP', 'Nike', 1, '', '2020-02-08'),
(10, 'SEP', 'Nike', 1, '', '2020-02-08'),
(11, 'SEP', 'Nike', 1, '', '2020-02-08'),
(12, 'SEP', 'Nike', 1, '', '2020-02-08'),
(13, 'SEP', 'Nike', 1, '', '2020-02-08'),
(14, 'SEP', 'Nike', 1, '', '2020-02-08'),
(15, 'SEP', 'Nike', 1, '', '2020-02-08'),
(2, 'BAT', 'DEF', 1, '', '2020-02-08'),
(3, 'BAT', 'DEF', 1, '', '2020-02-08'),
(4, 'BAT', 'DEF', 1, '', '2020-02-08'),
(5, 'BAT', 'DEF', 0, '', '2020-02-08'),
(6, 'BAT', 'DEF', 1, '', '2020-02-08'),
(7, 'BAT', 'DEF', 0, '', '2020-02-08'),
(1, 'DAS', 'Bi', 1, '', '2020-02-08'),
(2, 'DAS', 'Bi', 1, '', '2020-02-08'),
(3, 'DAS', 'Bi', 1, '', '2020-02-08'),
(4, 'DAS', 'Bi', 1, '', '2020-02-08'),
(5, 'DAS', 'Bi', 1, '', '2020-02-08'),
(1, 'INF', 'AOC', 1, '', '2020-02-08'),
(2, 'INF', 'AOC', 1, '', '2020-02-08'),
(3, 'INF', 'AOC', 1, '', '2020-02-08'),
(4, 'INF', 'AOC', 1, '', '2020-02-08'),
(5, 'INF', 'AOC', 1, '', '2020-02-08'),
(6, 'INF', 'AOC', 1, '', '2020-02-08'),
(7, 'INF', 'AOC', 1, '', '2020-02-08'),
(8, 'INF', 'AOC', 1, '', '2020-02-08'),
(9, 'INF', 'AOC', 1, '', '2020-02-08'),
(10, 'INF', 'AOC', 1, '', '2020-02-08'),
(11, 'INF', 'AOC', 1, '', '2020-02-08'),
(12, 'INF', 'AOC', 1, '', '2020-02-08'),
(13, 'INF', 'AOC', 1, '', '2020-02-08'),
(1, 'ROL', 'Bi', 1, '', '2020-02-08'),
(2, 'ROL', 'Bi', 1, '', '2020-02-08'),
(3, 'ROL', 'Bi', 1, '', '2020-02-08'),
(4, 'ROL', 'Bi', 1, '', '2020-02-08'),
(5, 'ROL', 'Bi', 1, '', '2020-02-08'),
(6, 'ROL', 'Bi', 1, '', '2020-02-08'),
(8, 'BAT', 'LG', 1, '', '2020-02-08'),
(2, 'LAP', 'LG', 1, '', '2020-02-08'),
(16, 'SEP', 'Adidas', 1, '', '2020-02-08'),
(17, 'SEP', 'Adidas', 1, '', '2020-02-08'),
(18, 'SEP', 'Adidas', 1, '', '2020-02-08'),
(7, 'ROL', 'Lg', 1, '', '2020-02-08'),
(8, 'ROL', 'Lg', 1, '', '2020-02-08'),
(9, 'ROL', 'Lg', 1, '', '2020-02-08'),
(10, 'ROL', 'Lg', 1, '', '2020-02-08'),
(11, 'ROL', 'Lg', 1, '', '2020-02-08'),
(1, 'TEL', 'LG', 1, '', '2020-02-08'),
(2, 'TEL', 'LG', 1, '', '2020-02-08'),
(3, 'TEL', 'LG', 1, '', '2020-02-08');

-- --------------------------------------------------------

--
-- Table structure for table `det_peminjaman`
--

CREATE TABLE `det_peminjaman` (
  `id_barang` varchar(10) NOT NULL,
  `id_detail` int(11) NOT NULL,
  `id_peminjaman` varchar(10) NOT NULL,
  `Status` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `det_peminjaman`
--

INSERT INTO `det_peminjaman` (`id_barang`, `id_detail`, `id_peminjaman`, `Status`) VALUES
('FLA', 1, '02073107', 2),
('MOU', 1, '02070411', 2),
('CDR', 1, '02070411', 2),
('BAT', 1, '02070411', 2),
('MOU', 4, '02070411', 2),
('SEP', 7, '02084845', 0),
('SEP', 6, '02084845', 0),
('SEP', 2, '02084845', 0),
('SEP', 3, '02084845', 0),
('SEP', 4, '02084845', 0),
('SEP', 5, '02084845', 0),
('SEP', 1, '02084845', 0),
('MOU', 3, '02084956', 0),
('MOU', 2, '02084956', 0),
('SEP', 15, '02085950', 0),
('BAT', 2, '02083143', 0),
('BAT', 7, '02083352', 2),
('BAT', 5, '02083352', 2),
('KEY', 1, '02083352', 2),
('SEP', 1, '02085632', 0),
('BAT', 2, '02111316', 0),
('BAT', 3, '02111316', 0),
('BAT', 4, '02111316', 0),
('BAT', 6, '02111316', 0),
('TEL', 1, '02111532', 0),
('TEL', 2, '02111532', 0),
('TEL', 3, '02111532', 0);

-- --------------------------------------------------------

--
-- Table structure for table `inventaris`
--

CREATE TABLE `inventaris` (
  `id_barang` varchar(10) NOT NULL,
  `nama_barang` varchar(15) NOT NULL,
  `jumlah` int(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `inventaris`
--

INSERT INTO `inventaris` (`id_barang`, `nama_barang`, `jumlah`) VALUES
('BAT', 'BATERAI', 8),
('CDR', 'CDREADER', 1),
('DAS', 'Dasi', 5),
('FLA', 'FLASHDISK', 1),
('INF', 'Infocus', 13),
('KEY', 'KEYBOARD', 2),
('LAP', 'LAPTOP', 2),
('MOU', 'MOUSE', 4),
('ROL', 'Roll 2 Meter', 11),
('SEP', 'Sepatu', 18),
('TEL', 'Television', 3);

-- --------------------------------------------------------

--
-- Table structure for table `peminjam`
--

CREATE TABLE `peminjam` (
  `id_peminjam` varchar(10) NOT NULL,
  `nama_pem` varchar(15) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `kelas` varchar(15) NOT NULL,
  `password` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `peminjaman`
--

CREATE TABLE `peminjaman` (
  `id_peminjaman` varchar(10) NOT NULL,
  `tgl_pinjam` date NOT NULL,
  `tgl_kembali` date NOT NULL,
  `id_petugas` varchar(10) DEFAULT NULL,
  `id_peminjam` varchar(10) DEFAULT NULL,
  `ruangan` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `peminjaman`
--

INSERT INTO `peminjaman` (`id_peminjaman`, `tgl_pinjam`, `tgl_kembali`, `id_petugas`, `id_peminjam`, `ruangan`) VALUES
('02070411', '2020-02-07', '2020-02-07', 'admin', NULL, 'A01'),
('02073107', '2020-02-07', '2020-02-07', 'admin', NULL, 'a1'),
('02083143', '2020-02-08', '2020-02-08', 'admin', NULL, 'jjjj'),
('02083352', '2020-02-08', '2020-02-02', 'admin', NULL, 'aaa8'),
('02084845', '2020-02-08', '2020-02-15', 'admin', NULL, 'A1'),
('02084956', '2020-02-08', '2020-02-09', 'admin', NULL, 'A2'),
('02085632', '2020-02-08', '2020-02-08', 'admin', NULL, 'A2'),
('02085950', '2020-02-08', '2020-02-09', 'admin', NULL, 'A1'),
('02111316', '2020-02-11', '2020-02-11', 'admin', NULL, 'A10'),
('02111532', '2020-02-11', '2020-02-12', 'admin', NULL, 'A1');

-- --------------------------------------------------------

--
-- Table structure for table `pengembalian`
--

CREATE TABLE `pengembalian` (
  `id_pengembalian` varchar(10) NOT NULL,
  `id_peminjaman` varchar(10) NOT NULL,
  `id_barang` varchar(10) NOT NULL,
  `id_detail` int(11) NOT NULL,
  `tgl_kembali` date NOT NULL,
  `id_petugas` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pengembalian`
--

INSERT INTO `pengembalian` (`id_pengembalian`, `id_peminjaman`, `id_barang`, `id_detail`, `tgl_kembali`, `id_petugas`) VALUES
('Sat0042', '02085950', 'SEP', 15, '2020-02-08', 'admin'),
('Sat3549', '02083143', 'BAT', 2, '2020-02-08', 'admin'),
('Sat4627', '02083352', 'BAT', 7, '2020-02-08', 'admin'),
('Sat4658', '02070411', 'MOU', 4, '2020-02-08', 'admin'),
('Sat4712', '02070411', 'MOU', 4, '2020-02-08', 'admin'),
('Sat5030', '02085632', 'SEP', 1, '2020-02-08', 'admin'),
('Sat5043', '02084956', 'MOU', 2, '2020-02-08', 'admin'),
('Sat5048', '02084956', 'MOU', 3, '2020-02-08', 'admin'),
('Sat5059', '02070411', 'MOU', 1, '2020-02-08', 'admin'),
('Sat5100', '02070411', 'MOU', 1, '2020-02-08', 'admin'),
('Sat5107', '02070411', 'MOU', 1, '2020-02-08', 'admin'),
('Sat5110', '02084845', 'SEP', 6, '2020-02-08', 'admin'),
('Sat5114', '02070411', 'CDR', 1, '2020-02-08', 'admin'),
('Sat5115', '02070411', 'CDR', 1, '2020-02-08', 'admin'),
('Sat5119', '02084845', 'SEP', 1, '2020-02-08', 'admin'),
('Sat5124', '02084845', 'SEP', 2, '2020-02-08', 'admin'),
('Sat5128', '02084845', 'SEP', 3, '2020-02-08', 'admin'),
('Sat5132', '02084845', 'SEP', 4, '2020-02-08', 'admin'),
('Sat5137', '02084845', 'SEP', 5, '2020-02-08', 'admin'),
('Sat5141', '02084845', 'SEP', 7, '2020-02-08', 'admin'),
('Sat5148', '02073107', 'FLA', 1, '2020-02-08', 'admin'),
('Sat5342', '02083352', 'BAT', 5, '2020-02-08', 'admin'),
('Sat5346', '02083352', 'KEY', 1, '2020-02-08', 'admin'),
('Tue1404', '02111316', 'BAT', 2, '2020-02-11', 'admin'),
('Tue1430', '02111316', 'BAT', 3, '2020-02-11', 'admin'),
('Tue1434', '02111316', 'BAT', 4, '2020-02-11', 'admin'),
('Tue1439', '02111316', 'BAT', 6, '2020-02-11', 'admin'),
('Tue1548', '02111532', 'TEL', 1, '2020-02-11', 'admin'),
('Tue1559', '02111532', 'TEL', 2, '2020-02-11', 'admin'),
('Tue1604', '02111532', 'TEL', 3, '2020-02-11', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `petugas`
--

CREATE TABLE `petugas` (
  `id_petugas` varchar(10) NOT NULL,
  `nama_pet` varchar(15) NOT NULL,
  `alamat` varchar(100) NOT NULL,
  `no_telp` varchar(15) NOT NULL,
  `password` varchar(20) NOT NULL,
  `level` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `petugas`
--

INSERT INTO `petugas` (`id_petugas`, `nama_pet`, `alamat`, `no_telp`, `password`, `level`) VALUES
('admin', 'arya', 'cipadu', '123123', 'admin', 2),
('geldut', 'geldy', 'graha', '0899', 'geldy', 2),
('kapka', 'kafkaa', 'pamulang', '08999', 'kafka', 2);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_inv`
-- (See below for the actual view)
--
CREATE TABLE `v_inv` (
`ID Barang` varchar(10)
,`Nama Barang` varchar(15)
,`Merek` varchar(15)
,`Ketersediaan` tinyint(1)
,`Keterangan` varchar(200)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_inventaris`
-- (See below for the actual view)
--
CREATE TABLE `v_inventaris` (
`ID Barang` varchar(10)
,`ID` int(11)
,`nama` varchar(15)
,`Merk` varchar(15)
,`Ketersediaan` tinyint(1)
,`Keterangan` varchar(200)
);

-- --------------------------------------------------------

--
-- Stand-in structure for view `v_pengembalian`
-- (See below for the actual view)
--
CREATE TABLE `v_pengembalian` (
`ID` varchar(10)
,`ID Peminjam` varchar(10)
,`ID Barang` varchar(10)
,`ID Detail` int(11)
,`nama Barang` varchar(15)
,`Ruangan` varchar(11)
,`Peminjam` varchar(8)
,`Status` tinyint(1)
,`Tanggal Pinjam` date
,`Tanggal Kembali` date
);

-- --------------------------------------------------------

--
-- Structure for view `v_inv`
--
DROP TABLE IF EXISTS `v_inv`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_inv`  AS  select `det`.`id_barang` AS `ID Barang`,`inv`.`nama_barang` AS `Nama Barang`,`det`.`merk` AS `Merek`,`det`.`ketersediaan` AS `Ketersediaan`,`det`.`keterangan` AS `Keterangan` from (`inventaris` `inv` join `detail_inv` `det`) where `inv`.`id_barang` = `det`.`id_barang` ;

-- --------------------------------------------------------

--
-- Structure for view `v_inventaris`
--
DROP TABLE IF EXISTS `v_inventaris`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_inventaris`  AS  select `inv2`.`id_barang` AS `ID Barang`,`di`.`id_detail` AS `ID`,`inv`.`nama_barang` AS `nama`,`di`.`merk` AS `Merk`,`di`.`ketersediaan` AS `Ketersediaan`,`di`.`keterangan` AS `Keterangan` from ((`detail_inv` `di` join `inventaris` `inv` on(`di`.`id_barang` = `inv`.`id_barang`)) join `inventaris` `inv2` on(`di`.`id_barang` = `inv2`.`id_barang`)) ;

-- --------------------------------------------------------

--
-- Structure for view `v_pengembalian`
--
DROP TABLE IF EXISTS `v_pengembalian`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_pengembalian`  AS  select distinct `pmj`.`id_peminjaman` AS `ID`,if(`pmj`.`id_petugas` = NULL,`pmj`.`id_peminjam`,`pmj`.`id_petugas`) AS `ID Peminjam`,`pmj2`.`id_barang` AS `ID Barang`,`pmj2`.`id_detail` AS `ID Detail`,`inv2`.`nama_barang` AS `nama Barang`,`pmj`.`ruangan` AS `Ruangan`,if(`pmj`.`id_petugas` = NULL,'Peminjam','Petugas') AS `Peminjam`,`pmj2`.`Status` AS `Status`,`pmj`.`tgl_pinjam` AS `Tanggal Pinjam`,`pmj`.`tgl_kembali` AS `Tanggal Kembali` from (((`peminjaman` `pmj` join `det_peminjaman` `pmj2` on(`pmj2`.`id_peminjaman` = `pmj`.`id_peminjaman`)) join `v_inventaris` `inv` on(`pmj2`.`id_detail` = `inv`.`ID`)) join `inventaris` `inv2` on(`pmj2`.`id_barang` = `inv2`.`id_barang`)) ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `denda`
--
ALTER TABLE `denda`
  ADD PRIMARY KEY (`id_denda`),
  ADD UNIQUE KEY `id_pengembalian` (`id_pengembalian`) USING BTREE,
  ADD KEY `id_petugas` (`id_petugas`);

--
-- Indexes for table `detail_inv`
--
ALTER TABLE `detail_inv`
  ADD KEY `id_barang` (`id_barang`),
  ADD KEY `id_detail` (`id_detail`);

--
-- Indexes for table `det_peminjaman`
--
ALTER TABLE `det_peminjaman`
  ADD KEY `id_peminjaman` (`id_peminjaman`),
  ADD KEY `id_detail` (`id_detail`) USING BTREE,
  ADD KEY `id_barang` (`id_barang`);

--
-- Indexes for table `inventaris`
--
ALTER TABLE `inventaris`
  ADD PRIMARY KEY (`id_barang`);

--
-- Indexes for table `peminjam`
--
ALTER TABLE `peminjam`
  ADD PRIMARY KEY (`id_peminjam`);

--
-- Indexes for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD PRIMARY KEY (`id_peminjaman`),
  ADD KEY `id_peminjam` (`id_peminjam`),
  ADD KEY `id_petugas` (`id_petugas`);

--
-- Indexes for table `pengembalian`
--
ALTER TABLE `pengembalian`
  ADD PRIMARY KEY (`id_pengembalian`),
  ADD KEY `id_peminjaman` (`id_peminjaman`) USING BTREE,
  ADD KEY `id_petugas` (`id_petugas`),
  ADD KEY `id_detail` (`id_detail`) USING BTREE,
  ADD KEY `id_barang` (`id_barang`);

--
-- Indexes for table `petugas`
--
ALTER TABLE `petugas`
  ADD PRIMARY KEY (`id_petugas`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `detail_inv`
--
ALTER TABLE `detail_inv`
  MODIFY `id_detail` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `detail_inv`
--
ALTER TABLE `detail_inv`
  ADD CONSTRAINT `detail_inv_ibfk_1` FOREIGN KEY (`id_barang`) REFERENCES `inventaris` (`id_barang`);

--
-- Constraints for table `det_peminjaman`
--
ALTER TABLE `det_peminjaman`
  ADD CONSTRAINT `det_peminjaman_ibfk_1` FOREIGN KEY (`id_detail`) REFERENCES `detail_inv` (`id_detail`),
  ADD CONSTRAINT `det_peminjaman_ibfk_2` FOREIGN KEY (`id_peminjaman`) REFERENCES `peminjaman` (`id_peminjaman`),
  ADD CONSTRAINT `det_peminjaman_ibfk_3` FOREIGN KEY (`id_barang`) REFERENCES `inventaris` (`id_barang`);

--
-- Constraints for table `peminjaman`
--
ALTER TABLE `peminjaman`
  ADD CONSTRAINT `peminjaman_ibfk_1` FOREIGN KEY (`id_peminjam`) REFERENCES `peminjam` (`id_peminjam`),
  ADD CONSTRAINT `peminjaman_ibfk_2` FOREIGN KEY (`id_petugas`) REFERENCES `petugas` (`id_petugas`);

--
-- Constraints for table `pengembalian`
--
ALTER TABLE `pengembalian`
  ADD CONSTRAINT `pengembalian_ibfk_1` FOREIGN KEY (`id_peminjaman`) REFERENCES `peminjaman` (`id_peminjaman`),
  ADD CONSTRAINT `pengembalian_ibfk_2` FOREIGN KEY (`id_detail`) REFERENCES `detail_inv` (`id_detail`),
  ADD CONSTRAINT `pengembalian_ibfk_3` FOREIGN KEY (`id_petugas`) REFERENCES `petugas` (`id_petugas`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
