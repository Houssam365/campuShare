#!/bin/bash
# Script d'exécution pour CampusShare
# Usage: ./run.sh

# Vérifier la compilation
if [ ! -d "target/classes" ]; then
    echo "❌ Projet non compilé. Exécutez d'abord: ./compile.sh"
    exit 1
fi

# Exécuter
java -cp target/classes com.campusshare.CampusShareGUI
