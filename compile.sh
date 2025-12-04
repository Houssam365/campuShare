#!/bin/bash
# Script de compilation pour CampusShare
# Usage: ./compile.sh

echo "╔════════════════════════════════════════════╗"
echo "║     Compilation de CampusShare             ║"
echo "╚════════════════════════════════════════════╝"
echo

# Vérifier Java
if ! command -v javac &> /dev/null; then
    echo "❌ Erreur: javac non trouvé. Installez Java JDK."
    exit 1
fi

echo "✓ Java trouvé: $(javac -version 2>&1)"
echo

# Créer le dossier de sortie
mkdir -p target/classes

# Compiler
echo "→ Compilation des fichiers Java..."
javac -d target/classes -sourcepath src/main/java $(find src/main/java -name "*.java")

if [ $? -eq 0 ]; then
    echo
    echo "✅ Compilation réussie!"
    echo "   Classes générées dans: target/classes/"
    echo
    echo "   Pour exécuter: ./run.sh"
else
    echo
    echo "❌ Erreur de compilation"
    exit 1
fi
