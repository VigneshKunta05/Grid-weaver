import { useEffect, useState } from 'react'
import { Client } from '@stomp/stompjs'
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
} from 'recharts'
import './App.css'

const API_BASE_URL =
    import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

const WS_URL =
    import.meta.env.VITE_WS_URL || 'ws://localhost:8080/ws'

function App() {
  const [gridData, setGridData] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [wsConnected, setWsConnected] = useState(false)
  const [chartData, setChartData] = useState([])

  useEffect(() => {

    // =========================
    // INITIAL DATA - REST API
    // =========================

    fetch(`${API_BASE_URL}/api/real-data/current`)
        .then((response) => {

          if (!response.ok) {
            throw new Error('Failed to fetch grid data')
          }

          return response.json()
        })

        .then((data) => {

          console.log('Initial grid data:', data)

          setGridData(data)
          setLoading(false)

          // First chart point
          setChartData([
            {
              time: new Date().toLocaleTimeString(),
              demand: data.currentDemand,
              generation: data.totalGeneration,
              gridImport: data.gridImport,
            },
          ])
        })

        .catch((err) => {

          console.error('API error:', err)

          setError(
              'Unable to connect to Grid-Weaver backend'
          )

          setLoading(false)
        })


    // =========================
    // WEBSOCKET CONNECTION
    // =========================

    const client = new Client({

      webSocketFactory: () =>
          new WebSocket(WS_URL),

      reconnectDelay: 5000,

      onConnect: () => {

        console.log('WebSocket connected')

        setWsConnected(true)


        client.subscribe(
            '/topic/real-grid-data',
            (message) => {

              try {

                const data = JSON.parse(message.body)

                console.log(
                    'Live grid data:',
                    data
                )


                // Update dashboard
                setGridData(data)


                // Add new point to chart
                setChartData(
                    (previousData) => {

                      const newPoint = {
                        time:
                            new Date().toLocaleTimeString(),

                        demand:
                        data.currentDemand,

                        generation:
                        data.totalGeneration,

                        gridImport:
                        data.gridImport,
                      }


                      const updatedData = [
                        ...previousData,
                        newPoint,
                      ]


                      // Keep latest 20 points
                      return updatedData.slice(-20)
                    }
                )

              } catch (err) {

                console.error(
                    'WebSocket message error:',
                    err
                )

              }

            }
        )
      },


      onDisconnect: () => {

        console.log(
            'WebSocket disconnected'
        )

        setWsConnected(false)

      },


      onStompError: (frame) => {

        console.error(
            'STOMP error:',
            frame
        )

        setWsConnected(false)

      },


      onWebSocketError: (error) => {

        console.error(
            'WebSocket error:',
            error
        )

        setWsConnected(false)

      },

    })


    client.activate()


    return () => {

      client.deactivate()

    }

  }, [])


  // =========================
  // LOADING
  // =========================

  if (loading) {

    return (

        <div className="loading-screen">

          <h2>
            ⚡ Grid-Weaver
          </h2>

          <p>
            Loading real-time grid data...
          </p>

        </div>

    )

  }


  // =========================
  // ERROR
  // =========================

  if (error) {

    return (

        <div className="loading-screen">

          <h2>
            ⚡ Grid-Weaver
          </h2>

          <p className="error-message">
            {error}
          </p>

          <p>
            Make sure Spring Boot is running
            on port 8080.
          </p>

        </div>

    )

  }


  // =========================
  // GENERATION MIX DATA
  // =========================

  const generationData = [
    {
      name: 'Thermal',
      value: gridData.thermalGeneration,
    },

    {
      name: 'Hydro',
      value: gridData.hydroGeneration,
    },

    {
      name: 'Wind',
      value: gridData.windGeneration,
    },

    {
      name: 'Gas',
      value: gridData.gasGeneration,
    },

    {
      name: 'Nuclear',
      value: gridData.nuclearGeneration,
    },

    {
      name: 'Solar',
      value: gridData.solarGeneration,
    },
  ].filter(
      (item) => item.value > 0
  )


  const generationColors = [
    '#ff7043',
    '#42a5f5',
    '#ab7cff',
    '#ffb74d',
    '#7e57c2',
    '#ffd54f',
  ]


  return (

      <div className="dashboard">


        {/* ================= HEADER ================= */}

        <header className="header">

          <div>

            <h1>
              ⚡ Grid-Weaver
            </h1>

            <p>
              Real-Time Smart Grid Monitoring
            </p>

          </div>


          <div className="live-status">

            <span className="live-dot"></span>

            {wsConnected
                ? 'LIVE'
                : 'CONNECTING'}

          </div>

        </header>


        {/* ================= SOURCE BAR ================= */}

        <div className="source-bar">

        <span>

          ● Data Source:{' '}

          <strong>
            NPP / MERIT India
          </strong>

        </span>


          <span>

          WebSocket:{' '}

            <strong>

            {wsConnected
                ? 'Connected'
                : 'Disconnected'}

          </strong>

        </span>

        </div>


        {/* ================= KPI CARDS ================= */}

        <section className="kpi-grid">


          {/* CURRENT DEMAND */}

          <div className="card">

          <span className="card-label">
            CURRENT DEMAND
          </span>

            <h2>

              {gridData.currentDemand.toLocaleString()}

              <small>
                {' '}MW
              </small>

            </h2>

            <p className="positive">
              ● Real-time demand
            </p>

          </div>


          {/* TOTAL GENERATION */}

          <div className="card">

          <span className="card-label">
            TOTAL GENERATION
          </span>

            <h2>

              {gridData.totalGeneration.toLocaleString()}

              <small>
                {' '}MW
              </small>

            </h2>

            <p className="positive">
              ● Generation online
            </p>

          </div>


          {/* GRID IMPORT */}

          <div className="card warning">

          <span className="card-label">
            GRID IMPORT
          </span>

            <h2>

              {gridData.gridImport.toLocaleString()}

              <small>
                {' '}MW
              </small>

            </h2>

            <p>
              Demand exceeds generation
            </p>

          </div>


          {/* GRID EXPORT */}

          <div className="card">

          <span className="card-label">
            GRID EXPORT
          </span>

            <h2>

              {gridData.gridExport.toLocaleString()}

              <small>
                {' '}MW
              </small>

            </h2>

            <p>
              No surplus generation
            </p>

          </div>

        </section>


        {/* ================= LIVE DEMAND CHART ================= */}

        <section className="chart-panel">

          <div className="panel-header">

            <div>

              <h2>
                Live Demand vs Generation
              </h2>

              <p>
                Real-time national power trend
              </p>

            </div>


            <span className="badge">

            {wsConnected
                ? 'LIVE'
                : 'OFFLINE'}

          </span>

          </div>


          <div className="chart-container">

            <ResponsiveContainer
                width="100%"
                height={360}
            >

              <LineChart
                  data={chartData}
                  margin={{
                    top: 10,
                    right: 20,
                    left: 10,
                    bottom: 10,
                  }}
              >

                <CartesianGrid
                    strokeDasharray="3 3"
                    stroke="#263247"
                />

                <XAxis
                    dataKey="time"
                    stroke="#7f8ea8"
                    tick={{
                      fontSize: 12,
                    }}
                />

                <YAxis
                    stroke="#7f8ea8"
                    tick={{
                      fontSize: 12,
                    }}
                    tickFormatter={(value) =>
                        `${Math.round(
                            value / 1000
                        )}k`
                    }
                />

                <Tooltip
                    contentStyle={{
                      backgroundColor:
                          '#101827',

                      border:
                          '1px solid #263247',

                      borderRadius:
                          '10px',

                      color:
                          '#ffffff',
                    }}

                    formatter={(value) =>
                        `${Number(
                            value
                        ).toLocaleString()} MW`
                    }
                />

                <Legend />


                <Line
                    type="monotone"
                    dataKey="demand"
                    name="Demand"
                    stroke="#4da3ff"
                    strokeWidth={3}
                    dot={false}
                    activeDot={{
                      r: 5,
                    }}
                />


                <Line
                    type="monotone"
                    dataKey="generation"
                    name="Generation"
                    stroke="#4ade9b"
                    strokeWidth={3}
                    dot={false}
                    activeDot={{
                      r: 5,
                    }}
                />


                <Line
                    type="monotone"
                    dataKey="gridImport"
                    name="Grid Import"
                    stroke="#ffb84d"
                    strokeWidth={2}
                    dot={false}
                    activeDot={{
                      r: 4,
                    }}
                />

              </LineChart>

            </ResponsiveContainer>

          </div>

        </section>


        {/* ================= LOWER CONTENT ================= */}

        <section className="content-grid">


          {/* ================= GRID OVERVIEW ================= */}

          <div className="panel">

            <div className="panel-header">

              <div>

                <h2>
                  Grid Overview
                </h2>

                <p>
                  Current national power balance
                </p>

              </div>


              <span className="badge">

              {wsConnected
                  ? 'LIVE'
                  : 'OFFLINE'}

            </span>

            </div>


            <div className="balance">


              {/* DEMAND */}

              <div className="balance-item">

              <span>
                Demand
              </span>

                <strong>

                  {gridData.currentDemand.toLocaleString()}
                  {' '}MW

                </strong>

                <div className="bar demand-bar"></div>

              </div>


              {/* GENERATION */}

              <div className="balance-item">

              <span>
                Generation
              </span>

                <strong>

                  {gridData.totalGeneration.toLocaleString()}
                  {' '}MW

                </strong>

                <div className="bar generation-bar"></div>

              </div>


              {/* GRID IMPORT */}

              <div className="balance-item">

              <span>
                Grid Import
              </span>

                <strong>

                  {gridData.gridImport.toLocaleString()}
                  {' '}MW

                </strong>

                <div className="bar import-bar"></div>

              </div>

            </div>

          </div>


          {/* ================= GENERATION MIX ================= */}

          <div className="panel">

            <div className="panel-header">

              <div>

                <h2>
                  Generation Mix
                </h2>

                <p>
                  Current generation by source
                </p>

              </div>

            </div>


            <div className="generation-list">


              {/* THERMAL */}

              <div className="generation-row">

              <span>
                🔥 Thermal
              </span>

                <strong>

                  {gridData.thermalGeneration.toLocaleString()}
                  {' '}MW

                </strong>

              </div>


              {/* HYDRO */}

              <div className="generation-row">

              <span>
                💧 Hydro
              </span>

                <strong>

                  {gridData.hydroGeneration.toLocaleString()}
                  {' '}MW

                </strong>

              </div>


              {/* WIND */}

              <div className="generation-row">

              <span>
                💨 Wind
              </span>

                <strong>

                  {gridData.windGeneration.toLocaleString()}
                  {' '}MW

                </strong>

              </div>


              {/* GAS */}

              <div className="generation-row">

              <span>
                ⚡ Gas
              </span>

                <strong>

                  {gridData.gasGeneration.toLocaleString()}
                  {' '}MW

                </strong>

              </div>


              {/* NUCLEAR */}

              <div className="generation-row">

              <span>
                ☢️ Nuclear
              </span>

                <strong>

                  {gridData.nuclearGeneration.toLocaleString()}
                  {' '}MW

                </strong>

              </div>


              {/* SOLAR */}

              <div className="generation-row">

              <span>
                ☀️ Solar
              </span>

                <strong>

                  {gridData.solarGeneration.toLocaleString()}
                  {' '}MW

                </strong>

              </div>

            </div>

          </div>

        </section>


        {/* ================= GENERATION DONUT CHART ================= */}

        <section className="chart-panel">

          <div className="panel-header">

            <div>

              <h2>
                Generation Source Distribution
              </h2>

              <p>
                Current generation by source
              </p>

            </div>


            <span className="badge">
            LIVE
          </span>

          </div>


          <div className="chart-container">

            <ResponsiveContainer
                width="100%"
                height={360}
            >

              <PieChart>

                <Pie
                    data={generationData}
                    dataKey="value"
                    nameKey="name"
                    cx="50%"
                    cy="50%"
                    innerRadius={85}
                    outerRadius={130}
                    paddingAngle={3}
                >

                  {generationData.map(
                      (entry, index) => (

                          <Cell
                              key={`cell-${entry.name}`}
                              fill={
                                generationColors[
                                index %
                                generationColors.length
                                    ]
                              }
                          />

                      )
                  )}

                </Pie>


                <Tooltip
                    contentStyle={{
                      backgroundColor:
                          '#101827',

                      border:
                          '1px solid #263247',

                      borderRadius:
                          '10px',

                      color:
                          '#ffffff',
                    }}

                    formatter={(value) =>
                        `${Number(
                            value
                        ).toLocaleString()} MW`
                    }
                />

              </PieChart>

            </ResponsiveContainer>

          </div>

        </section>


        {/* ================= FOOTER ================= */}

        <footer>

        <span>
          Grid-Weaver
        </span>

          <span>
          Spring Boot + React + WebSocket
        </span>

          <span>
          NPP / MERIT India
        </span>

        </footer>

      </div>

  )
}

export default App