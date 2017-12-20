-- phpMyAdmin SQL Dump
-- version 4.7.3
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost:3306
-- Généré le :  jeu. 21 déc. 2017 à 00:29
-- Version du serveur :  5.6.35
-- Version de PHP :  7.1.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

--
-- Base de données :  `baccalaureat`
--

-- --------------------------------------------------------

--
-- Structure de la table `assoccomposer`
--

CREATE TABLE `assoccomposer` (
  `code` varchar(10) NOT NULL,
  `libelle` varchar(30) NOT NULL,
  `codeepreuve` varchar(10) NOT NULL,
  `epreuve` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `assocdeterminer`
--

CREATE TABLE `assocdeterminer` (
  `serie` varchar(5) NOT NULL,
  `mention` varchar(5) NOT NULL,
  `specialite` varchar(5) NOT NULL,
  `opt` varchar(5) NOT NULL,
  `code` varchar(10) NOT NULL,
  `libelle` varchar(30) NOT NULL,
  `coefficient` varchar(2) NOT NULL,
  `composition` varchar(2) NOT NULL,
  `bonus` varchar(2) NOT NULL,
  `facultatif` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `assocpreciser`
--

CREATE TABLE `assocpreciser` (
  `code1` varchar(10) NOT NULL,
  `libelle1` varchar(30) NOT NULL,
  `code2` varchar(10) NOT NULL,
  `libelle2` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `assocremplacer`
--

CREATE TABLE `assocremplacer` (
  `g1` varchar(10) NOT NULL,
  `g2` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `attrgroupes`
--

CREATE TABLE `attrgroupes` (
  `code` varchar(10) NOT NULL,
  `groupe` varchar(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `tabepreuves`
--

CREATE TABLE `tabepreuves` (
  `code` varchar(10) NOT NULL,
  `libelle` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `tabmatiere`
--

CREATE TABLE `tabmatiere` (
  `code` varchar(10) NOT NULL,
  `libelle` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `tabnotes`
--

CREATE TABLE `tabnotes` (
  `id` int(10) NOT NULL,
  `code1` varchar(10) NOT NULL,
  `libelle1` varchar(30) NOT NULL,
  `code2` varchar(10) NOT NULL,
  `libelle2` varchar(30) NOT NULL,
  `note` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `tabprofils`
--

CREATE TABLE `tabprofils` (
  `serie` varchar(5) NOT NULL,
  `mention` varchar(5) NOT NULL,
  `specialite` varchar(5) NOT NULL,
  `section` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `tabscandidats`
--

CREATE TABLE `tabscandidats` (
  `libelleexamen` varchar(30) NOT NULL,
  `anneesession` varchar(4) NOT NULL,
  `moissession` varchar(2) NOT NULL,
  `serie` varchar(5) NOT NULL,
  `mention` varchar(5) NOT NULL,
  `specialite` varchar(5) NOT NULL,
  `section` varchar(5) NOT NULL,
  `numerocandidat` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
