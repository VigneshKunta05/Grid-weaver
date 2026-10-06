# ⚡ Grid-Weaver

### Real-Time Smart Grid Monitoring & Energy Management System

Grid-Weaver is a full-stack smart grid monitoring application built with **Spring Boot, React, WebSocket, REST APIs, and real-time Indian power-grid data**.

The system collects live grid information from the **National Power Portal (NPP)**, processes generation and demand data, and presents it through an interactive real-time dashboard.

---

## 🌐 Live Demo

### Frontend
https://grid-weaver-frontend.onrender.com

### Backend API
https://grid-weaver.onrender.com

### Current Grid Data
https://grid-weaver.onrender.com/api/real-data/current

---

# 📌 Project Overview

Grid-Weaver provides a real-time view of India's electricity grid by monitoring:

- ⚡ Total electricity demand
- 🔋 Total generation
- 📥 Grid import
- 📤 Grid export
- ☀️ Solar generation
- 💨 Wind generation
- 💧 Hydro generation
- 🔥 Thermal generation
- ⚛️ Nuclear generation
- 🔥 Gas generation

The backend fetches real grid data from the **National Power Portal (NPP)** and exposes it through REST APIs.

The dashboard receives real-time updates using **WebSocket/STOMP**.

---

# ✨ Features

## ⚡ Real-Time Grid Monitoring

The dashboard displays live:

- Current Demand
- Total Generation
- Grid Import
- Grid Export

Data is retrieved from the National Power Portal.

---

## 📊 Generation Mix

Grid-Weaver breaks down electricity generation into major sources:

| Source | Description |
|---|---|
| Thermal | Thermal power generation |
| Hydro | Hydroelectric generation |
| Solar | Solar generation |
| Wind | Wind generation |
| Gas | Gas-based generation |
| Nuclear | Nuclear generation |

The dashboard visualizes the generation mix using interactive charts.

---

## 🔄 WebSocket Real-Time Updates

Grid-Weaver uses WebSocket communication for live dashboard updates.

### WebSocket Endpoint

```text
/ws