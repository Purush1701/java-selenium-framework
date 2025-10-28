#!/bin/bash

# Maven wrapper script for Selenium Framework
# This script provides easy commands to run tests and manage the project

# Function to open the latest test report
open_report() {
    echo "Opening test report..."
    if [ -f test-output/reports/TestReport_*.html ]; then
        open test-output/reports/TestReport_*.html
        echo "✅ Report opened in browser"
    else
        echo "❌ No report found in test-output/reports/"
    fi
}

case "$1" in
    "install")
        echo "Installing dependencies..."
        mvn clean install
        ;;
    "test")
        echo "Running all tests..."
        mvn clean test
        open_report
        ;;
    "test-chrome")
        echo "Running tests with Chrome browser..."
        mvn clean test -Dbrowser=chrome
        open_report
        ;;
    "test-firefox")
        echo "Running tests with Firefox browser..."
        mvn clean test -Dbrowser=firefox
        open_report
        ;;
    "test-headless")
        echo "Running tests in headless Chrome mode..."
        mvn clean test -Dbrowser=chrome
        open_report
        ;;
    "test-only")
        echo "Running tests without opening report..."
        mvn test
        ;;
    "report")
        echo "Opening the latest test report..."
        open_report
        ;;
    "clean")
        echo "Cleaning project and reports..."
        mvn clean
        ;;
    "compile")
        echo "Compiling project..."
        mvn compile
        ;;
    "help"|*)
        echo "═══════════════════════════════════════════════════"
        echo "  Selenium Framework Maven Wrapper"
        echo "═══════════════════════════════════════════════════"
        echo "Usage: ./mvn-wrapper.sh [command]"
        echo ""
        echo "Commands:"
        echo "  install       - Install all dependencies"
        echo "  test          - Run all tests (Firefox) + open report"
        echo "  test-chrome   - Run tests with Chrome + open report"
        echo "  test-firefox  - Run tests with Firefox + open report"
        echo "  test-headless - Run tests in headless mode + open report"
        echo "  test-only     - Run tests without opening report"
        echo "  report        - Open the latest test report"
        echo "  clean         - Clean project and old reports"
        echo "  compile       - Compile project only"
        echo "  help          - Show this help"
        echo ""
        echo "Examples:"
        echo "  ./mvn-wrapper.sh test"
        echo "  ./mvn-wrapper.sh test-chrome"
        echo "  ./mvn-wrapper.sh report"
        echo "═══════════════════════════════════════════════════"
        ;;
esac
