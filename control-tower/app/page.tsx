"use client";
import Navbar from "./components/Navbar";
import {Check,Network,AlertTriangle,Brain} from "lucide-react";
import {
  IndianRupee,
  Banknote,
  Landmark,
  Wallet,
  Building2,
} from "lucide-react";
import TransactionCard from "./components/TransactionCard";
import GreenIconCircle from "./components/GreenIconCircle";
import CapabilityCard from "./components/CapabilityCard";

export default function Page() {
  return(
    <div>
      <Navbar/>
     

      {/* HERO SECTION */}
        <div className="grid  grid-cols-1 lg:grid-cols-2 gap-6 p-8 lg:h-screen ">
          {/* Left side content */}
          <div className="flex flex-col justify-center items-start gap-4 pb-10">
             <div className="p-3  bg-gray-900 rounded-3xl ml-2  flex gap-3 text-xs lg:w-1/2">
              <span className="flex items-center justify-center w-5 h-5 rounded-full bg-lime-500">
                 <Check className="w-3 h-3 text-black" strokeWidth={3} />
              </span>
              <h2 className=" flex items-center font-bold">AI-Powered | Real-Time Financial Crime Detection</h2>
             </div>

             <div className="px-3 pt-3 text-3xl font-helvitica-neue">
              <h2>Welcome to MuleHunter</h2>
              <h2>Detecting Financial Fraud Before It Spreads</h2>
              <h3 className="text-[#CAFF33]">Fraud Networks</h3>

             </div>

             <div className="px-3 text-gray-500">MuleHunter is an intelligent fraud detection and visual analytics platform designed to identify mule accounts, collusive transaction rings, and anomalous financial behavior in large-scale payment ecosystems like UPI.

By combining graph analytics, machine learning, and explainable AI, MuleHunter empowers investigators to detect, analyze, and act on financial crime with speed and confidence.</div>

            {/* CTA Button */}
            <button className="bg-[#caff33] text-black text-sm p-3 px-4 ml-3 rounded-3xl">Explore Fraud Network</button>
          </div>
          {/* Right side content */}
          <div className="flex flex-col justify-center items-center gap-4">
            
            <div className="border  w-100 flex flex-col gap-3 p-5  border-gray-900 rounded-2xl text-xs " >
              <h1 className="text-xs font-bold">Live Fraud Activity</h1>
                            
              <TransactionCard nodeId={1024} risk="HIGH" amount={68000} />
              <TransactionCard nodeId={1871} risk="MEDIUM" amount={41500} />
              <TransactionCard nodeId={449} risk="LOW" amount={2300} />
              <h2 className="text-xs font-bold">Risk Signals</h2>
              <div className="flex flex-col justify-between items-center border-gray-900 border p-3 gap-3 ">
                <h3>Detected Indicators</h3>
                <h3 className="text-sm">🔺 High Velocity Transfers</h3>
                <h3 className="text-sm">🔺 Multiple Inbound Sources</h3>
                <h3 className="text-sm">🔺 Circular Transaction Pattern</h3>
              </div>

              <div className="px-4 py-3 bg-[#2f361e] rounded-xl items-center flex justify-center text-[#98b830] mx-4">Explain Why This Is Fraud</div>

            </div>
            <div className=" p-4 border border-gray-900 rounded-3xl  flex  items-center gap-3 justify-between ">
              <div className="text-xs">Supported Networks</div>
              <GreenIconCircle icon={IndianRupee} size="xs" />
              <GreenIconCircle icon={Banknote} size="xs" />
              <GreenIconCircle icon={Landmark} size="xs" />
              <GreenIconCircle icon={Wallet} size="xs" />
              <GreenIconCircle icon={Building2} size="xs" />

            </div>
            
          </div>
        </div>

      {/*Core Capabilities  */}
         <div className="flex gap-4 flex-col">
          <div>
            <span className="text-2xl font-bold pl-8">Core </span><span className="text-2xl font-bold text-[#caff33]">Capabilities</span>
          </div>
          

          <div className="text-sm pl-8 text-gray-600">MuleHunter provides a unified intelligence layer for detecting, analyzing, and explaining financial fraud networks at scale.</div>

          <div className="grid grid-cols-1 lg:grid-cols-3 py-8 px-15 gap-6">
            <CapabilityCard icon={Network} title="Graph-Based Fraud Detection" points={[
             "Detect mule rings and collusive behavior using transaction graph analysis",
             "Identify hidden communities and circular money flows",
              ]}
            />
            <CapabilityCard icon={AlertTriangle} title="Anomaly & Risk Scoring" points={[
             "Machine learning models flag high-risk nodes and transactions",
             "Velocity, volume, and behavioral deviation analysis",
              ]}
            />
            <CapabilityCard icon={Brain} title="Explainable AI (XAI)" points={[
             "SHAP-powered feature attribution",
             "Human-readable AI explanations for investigators and auditors",
              ]}
            />
          </div>
         </div>

      {/* Use Cases  */}
      <div className="flex gap-3 flex-col">
          <div>
            <span className="text-2xl font-bold pl-8">Use </span><span className="text-2xl font-bold text-[#caff33]">Cases</span>
          </div>
           <div className="text-sm pl-8 text-gray-600">MuleHunter is built for investigators and institutions to uncover mule networks, explain risk, and act on financial fraud with confidence.</div>
        
      </div>

    </div>
  )
}