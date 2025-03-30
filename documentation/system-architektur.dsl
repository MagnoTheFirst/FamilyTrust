workspace {

    model {
        user = person "Benutzer" {
            description "Nutzer der Family Trust Anwendung"
        }

        familyTrustSystem = softwareSystem "Family Trust System" {
            description "Dokumentiert den Family Trust inklusive Überweisungen und Investitionen"

            api = container "API" {
                description "Spring Boot Backend"
                technology "Java + Spring Boot"
            }

            database = container "Datenbank" {
                description "PostgreSQL Datenbank"
                technology "PostgreSQL"
            }

            domainModel = container "Domain Model" {
                description "Das Kern-Domänenmodell der Anwendung"

                ueberweisung = component "Ueberweisung" {
                    description "Repräsentiert eine Überweisung"
                }

                investment = component "Investment" {
                    description "Repräsentiert eine Investition in Gold, Silber, Aktien oder ETFs"
                }

                verkauf = component "Verkauf" {
                    description "Repräsentiert einen Verkauf einer Investition"
                }

                investmentTyp = component "InvestmentTyp" {
                    description "Typenum für Investmentarten: GOLD, SILBER, AKTIE, ETF"
                }

                ueberweisung -> investment "finanziert"
                investment -> verkauf "wird verkauft"
                investment -> investmentTyp "hat Typ"
            }

            user -> api "Verwendet"
            api -> domainModel "Verwendet Domain Model"
            api -> database "Liest und schreibt"
            domainModel -> database "persistiert in"
        }
    }

    views {
        systemContext familyTrustSystem {
            include *
            autolayout lr
            title "Family Trust - System Context"
        }

        container familyTrustSystem {
            include *
            autolayout lr
            title "Family Trust - Container View"
        }

        component domainModel {
            include *
            autolayout lr
            title "Family Trust - Domain Model (Components)"
        }

        theme default
    }
}
