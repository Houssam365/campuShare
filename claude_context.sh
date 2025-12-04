#!/bin/bash
#
# CampusShare - Context Generation Script
# This script gathers all Java source code and Documentation,
# then appends the architectural prompt to create a comprehensive context file.
#

OUTPUT_FILE="claude_context.txt"

echo "--- Generating complete context for CampusShare ---"

# --- Step 1: Clear previous context for a fresh start ---
echo "[1/4] Clearing old context file..."
> "$OUTPUT_FILE"

# --- Step 2: Append Java Source Code ---
echo "[2/4] Appending Java source files (*.java)..."
# Finds all .java files in src/main
find src/main -name "*.java" -exec sh -c '
  echo "File: $1" >> "'"$OUTPUT_FILE"'" && cat "$1" >> "'"$OUTPUT_FILE"'" && echo -e "\n\n--- END OF FILE ---\n\n" >> "'"$OUTPUT_FILE"'"
' sh {} \;

# --- Step 3: Append Documentation & Readmes ---
echo "[3/4] Appending documentation files (*.md)..."
# Finds READMEs in root and docs folder
find . -maxdepth 3 -name "*.md" -not -path "*/target/*" -exec sh -c '
  echo "File: $1" >> "'"$OUTPUT_FILE"'" && cat "$1" >> "'"$OUTPUT_FILE"'" && echo -e "\n\n--- END OF FILE ---\n\n" >> "'"$OUTPUT_FILE"'"
' sh {} \;

# --- Step 4: Append Directory Trees & Final Prompt ---
echo "[4/4] Appending directory trees and project prompt..."
{
  echo "--- DIRECTORY STRUCTURE ---"
  echo ""
  echo "Source Tree:"
  # If 'tree' command is not installed, this might fail gracefully or you can use 'find'
  if command -v tree &> /dev/null; then
      tree src/main
  else
      find src/main -maxdepth 4
  fi
  echo ""
  echo "Docs Tree:"
  if command -v tree &> /dev/null; then
      tree docs
  else
      find docs -maxdepth 2
  fi
  echo ""
  echo "---------------------------"
  echo ""
} >> "$OUTPUT_FILE"

# Append the specific architectural context for CampusShare
cat <<'EOT' >> "$OUTPUT_FILE"
Project Context: CampusShare - Student Exchange Platform
Course: INFO 732 (Design & Realization of Information Systems)

Core Concept: 
A desktop application allowing students to exchange goods (selling), services (tutoring), and donations. The project focuses strictly on Software Architecture and Design Patterns.

Technological Constraints:
- Language: Java (Standard Edition).
- UI: Console-based (System.out/System.in).
- Persistence: In-memory only (Data is lost on restart).

Architectural Requirements (Design Patterns):

1. Factory Pattern (Creational):
   - Location: `pattern.factory`
   - Role: Centralizes creation of advertisements (`Bien`, `Service`, `Don`).
   - Why: Encapsulates validation logic (e.g., Price must be 0 for Donations) and decouples the client from specific subclasses.

2. Observer Pattern (Behavioral):
   - Location: `pattern.observer`
   - Role: `Annonce` (Subject) notifies `Etudiant` (Observer).
   - Why: Users subscribe to ads. When an ad status changes (e.g., SOLD), subscribers are notified instantly without tight coupling.

3. Strategy Pattern (Behavioral) - Payment:
   - Location: `pattern.strategy`
   - Role: `IPaiementStrategy` defines how a transaction is processed.
   - Implementations: `PaiementPoints` (Virtual currency), `PaiementGratuit` (Donations), `PaiementCarteSimule`.

4. Strategy Pattern (Behavioral) - Search:
   - Location: `pattern.strategy`
   - Role: `ITriStrategy` defines sorting algorithms.
   - Implementations: Sort by Price, Sort by Date, Sort by Popularity.

5. Adapter Pattern (Structural):
   - Location: `pattern.adapter`
   - Role: Connects the internal `ICalendrier` interface to a (Mocked) External Google API.
   - Why: Allows the system to schedule exchanges on a calendar without depending directly on the external library's API structure.

Team Workflow:
- Member 1: Users & Observer Pattern.
- Member 2: Catalog & Factory Pattern.
- Member 3: Finance & Payment Strategy.
- Member 4: Search Strategy & Calendar Adapter.

EOT

echo "--- Context generation complete. File '$OUTPUT_FILE' is ready. ---"