/**
 *  ****************  Motion Controlled Scene Lighting Child App  ****************
 *
 *  Automate your lights based on Motion and Current Mode, utilizing Hubitat 'Scenes'.
 *  Hubitat has all these great built-in apps. This app brings a few of them together!
 *		- Uses 'Modes', 'Groups and Scenes' and 'Zone Motion Controller'
 *		- Motion is the key to making everything happen. User can select individual motion sensors
 *		  or choose a Zone Motion Controller device
 *		- Turn lights on/off based on Mode, utilizing Hubitat Scenes
 *		- Optionally: Can also use light level as a condition
 *		- Select up to 8 different Modes/Scene combinations per child app
 *		- Each child app has a Lights Dim Warning when X amount of time has passed without motion
 *		- If still no motion after Dim Warning, turn off all lights in Scene after X amount of time
 *		- When motion is activated, lights are reset to the appropriate Scene
 *		- Each child app also has a Safety Net which turns off any user selected lights after X
 *		  amount of time has passed. Can be any lights, in Scene or not. (ie. all lights in room)
 *		- Ability to pause any child app
 *		- Ability to Enable/Disable child app via a switch
 *		- Parent/Child App structure
 *		- Create as many child apps as needed
 *		- Displays the current Mode next to the Parent App Name for easy reference
 *
 *	
 *  Copyright 2018 Bryan Turcotte (@bptworld)
 *
 *  Special thanks to (@Cobra) for use of his Parent/Child code and various other bits and pieces.
 *
 *  Also thanks to Stephan Hackett (@stephacka) for sharing his code/app on how to dim lights when there is no motion.
 *  
 *  This App is free.  If you like and use this app, please be sure to give a shout out on the Hubitat forums to let
 *  people know that it exists!  Thanks.
 *
 *  Remember...I am not a programmer, everything I do takes a lot of time and research!
 *  Donations are never necessary but always appreciated.  Donations to support development efforts are accepted via: 
 *
 *  Paypal at: https://paypal.me/bptworld
 *
 *-------------------------------------------------------------------------------------------------------------------
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 * ------------------------------------------------------------------------------------------------------------------------------
 *
 *  If modifying this project, please keep the above header intact and add your comments/credits below - Thank you! -  @BPTWorld
 *
 *  App and Driver updates can be found at https://github.com/bptworld/Hubitat/
 *
 * ------------------------------------------------------------------------------------------------------------------------------
 *
 *  Changes:
 *					.
 *  V1.0.0 - 12/19/18 - Initial release.
 *
 */

definition(
    name: "Motion Controlled Scene Lighting Child",
    namespace: "BPTWorld",
    author: "Bryan Turcotte",
    description: "Automate your lights based on Motion and Current Mode, utilizing Hubitat 'Scenes'.",
    category: "",
    
parent: "BPTWorld:Motion Controlled Scene Lighting",
    
    iconUrl: "",
    iconX2Url: "",
    iconX3Url: "",
    )

preferences {
    page(name: "pageConfig")
}

def pageConfig() {
    dynamicPage(name: "", title: "", install: true, uninstall: true, refreshInterval:0) {	
    display()
		section("Instructions:", hideable: true, hidden: true) {
        	paragraph "<b>Info:</b>"
    		paragraph "Automate your lights based on Motion and Current Mode, utilizing Hubitat 'Scenes'."
			paragraph "<b>Prerequisites:</b>"
			paragraph "- Must already have at least one Scene setup in Hubitats 'Groups and Scenes' built in app.<br>- Have at least one dimmable buld included in each Scene.<br>- (Optional) Have a virutal switch created to Enable/Disable each child app."
		}
    	section() {
			input "motionSensors", "capability.motionSensor", title: "Select Motion Sensor(s):", required: true, multiple:true
		}
		section("<b>Mode 1</b>") {
			input "modeName1", "mode", title: "When in this Mode...", required: true, multiple: false, width:6
			input "sceneSwitch1", "capability.switch", title: "...turn on this Switch to Activate Scene.", required: true, multiple: false, width:6
			input "lightsToDim1", "capability.switch", title: "Select light(s) to dim when no motion (will dim to 50% of whatever level they are currently)", required: true, multiple: true
			input "dimTime1", "number", title: "Time since Motion inactivity, before lights selected dim (in minutes)", required: true, width:6
        	input "offTime1", "number", title: "Time since lights Dimmed, before all lights in Scene turn off (in seconds)", required: true, width:6
		}
		section() {input(name: "mode2Enable", type: "bool", defaultValue: "false", submitOnChange: true, title: "Need another Mode?", description: "Enable another mode set.")}
		if(mode2Enable) {
			section("<b>Mode 2</b>") {
				input "modeName2", "mode", title: "When in this Mode...", required: true, multiple: false, width:6
				input "sceneSwitch2", "capability.switch", title: "...turn on this Switch to Activate Scene.", required: true, multiple: false, width:6
				input "lightsToDim2", "capability.switch", title: "Select light(s) to dim when no motion (will dim to 50% of whatever level they are currently)", required: true, multiple: true
				input "dimTime2", "number", title: "Time since Motion inactivity, before lights selected dim (in minutes)", required: true, width:6
        		input "offTime2", "number", title: "Time since lights Dimmed, before all lights in Scene turn off (in seconds)", required: true, width:6
			}
			section() {input(name: "mode3Enable", type: "bool", defaultValue: "false", submitOnChange: true, title: "Need another Mode?", description: "Enable another mode set.")}
		}
		if(mode3Enable) {	
			section("<b>Mode 3</b>") {	
				input "modeName3", "mode", title: "When in this Mode...", required: true, multiple: false, width:6
				input "sceneSwitch3", "capability.switch", title: "...turn on this Switch to Activate Scene.", required: true, multiple: false, width:6
				input "lightsToDim3", "capability.switch", title: "Select light(s) to dim when no motion (will dim to 50% of whatever level they are currently)", required: true, multiple: true
				input "dimTime3", "number", title: "Time since Motion inactivity, before lights selected dim (in minutes)", required: true, width:6
        		input "offTime3", "number", title: "Time since lights Dimmed, before all lights in Scene turn off (in seconds)", required: true, width:6
			}
			section() {input(name: "mode4Enable", type: "bool", defaultValue: "false", submitOnChange: true, title: "Need another Mode?", description: "Enable another mode set.")}
		}
		if(mode4Enable) {
			section("<b>Mode 4</b>") {	
				input "modeName4", "mode", title: "When in this Mode...", required: true, multiple: false, width:6
				input "sceneSwitch4", "capability.switch", title: "...turn on this Switch to Activate Scene.", required: true, multiple: false, width:6
				input "lightsToDim4", "capability.switch", title: "Select light(s) to dim when no motion (will dim to 50% of whatever level they are currently)", required: true, multiple: true
				input "dimTime4", "number", title: "Time since Motion inactivity, before lights selected dim (in minutes)", required: true, width:6
        		input "offTime4", "number", title: "Time since lights Dimmed, before all lights in Scene turn off (in seconds)", required: true, width:6
			}
			section() {input(name: "mode5Enable", type: "bool", defaultValue: "false", submitOnChange: true, title: "Need another Mode?", description: "Enable another mode set.")}
		}
		if(mode5Enable) {
			section("<b>Mode 5</b>") {	
				input "modeName5", "mode", title: "When in this Mode...", required: true, multiple: false, width:6
				input "sceneSwitch5", "capability.switch", title: "...turn on this Switch to Activate Scene.", required: true, multiple: false, width:6
				input "lightsToDim5", "capability.switch", title: "Select light(s) to dim when no motion (will dim to 50% of whatever level they are currently)", required: true, multiple: true
				input "dimTime5", "number", title: "Time since Motion inactivity, before lights selected dim (in minutes)", required: true, width:6
        		input "offTime5", "number", title: "Time since lights Dimmed, before all lights in Scene turn off (in seconds)", required: true, width:6
			}
			section() {input(name: "mode6Enable", type: "bool", defaultValue: "false", submitOnChange: true, title: "Need another Mode?", description: "Enable another mode set.")}
		}
		if(mode6Enable) {
			section("<b>Mode 6</b>") {	
				input "modeName6", "mode", title: "When in this Mode...", required: true, multiple: false, width:6
				input "sceneSwitch6", "capability.switch", title: "...turn on this Switch to Activate Scene.", required: true, multiple: false, width:6
				input "lightsToDim6", "capability.switch", title: "Select light(s) to dim when no motion (will dim to 50% of whatever level they are currently)", required: true, multiple: true
				input "dimTime6", "number", title: "Time since Motion inactivity, before lights selected dim (in minutes)", required: true, width:6
        		input "offTime6", "number", title: "Time since lights Dimmed, before all lights in Scene turn off (in seconds)", required: true, width:6
			}
		}
		if(mode7Enable) {
			section("<b>Mode 7</b>") {	
				input "modeName7", "mode", title: "When in this Mode...", required: true, multiple: false, width:6
				input "sceneSwitch7", "capability.switch", title: "...turn on this Switch to Activate Scene.", required: true, multiple: false, width:6
				input "lightsToDim7", "capability.switch", title: "Select light(s) to dim when no motion (will dim to 50% of whatever level they are currently)", required: true, multiple: true
				input "dimTime7", "number", title: "Time since Motion inactivity, before lights selected dim (in minutes)", required: true, width:6
        		input "offTime7", "number", title: "Time since lights Dimmed, before all lights in Scene turn off (in seconds)", required: true, width:6
			}
		}
		if(mode8Enable) {
			section("<b>Mode 8</b>") {	
				input "modeName8", "mode", title: "When in this Mode...", required: true, multiple: false, width:6
				input "sceneSwitch8", "capability.switch", title: "...turn on this Switch to Activate Scene.", required: true, multiple: false, width:6
				input "lightsToDim8", "capability.switch", title: "Select light(s) to dim when no motion (will dim to 50% of whatever level they are currently)", required: true, multiple: true
				input "dimTime8", "number", title: "Time since Motion inactivity, before lights selected dim (in minutes)", required: true, width:6
        		input "offTime8", "number", title: "Time since lights Dimmed, before all lights in Scene turn off (in seconds)", required: true, width:6
			}
		}
		section("<b>Restrictions</b> (optional)") {
        	input "lightSensor", "capability.illuminanceMeasurement", title: "Only when illuminance on this light sensor...", required: false, width:6
            input "lightLevel", "number", title: "...is equal to or below this illuminance level", required: false, width:6
		}
		section("<b>Safety Net</b> (optional - used to turn off all lights selected, not just the ones in the Scene.)") {
			input "safetySwitches", "capability.switch", title: "Turn off these switches when...", required: false, multiple: true, width:6
			input "safetyTime", "number", title: "...Time since Motion inactivity (in minutes)", required: false, width:6
		}
		section("<b>General</b>") {label title: "Enter a name for this child app", required: false}
		section() {
			input(name: "enablerSwitch1", type: "capability.switch", title: "Enable/Disable child app with this switch - If Switch is ON then app is disabled, if Switch is OFF then app is active.", required: false, multiple: false)
			input(name: "logEnable", type: "bool", defaultValue: "true", title: "Enable Debug Logging", description: "Enable extra logging for debugging.")
    	}
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}

def initialize() {
	logCheck()
    setDefaults()
	subscribe(location, "mode", modeHandler)
    subscribe(enablerSwitch1, "switch", enablerSwitchHandler)
    subscribe(motionSensors, "motion", motionHandler)
}

def enablerSwitchHandler(evt){
	state.enablerSwitch2 = evt.value
	LOGDEBUG("IN enablerSwitchHandler - Enabler Switch = ${enablerSwitch2}")
	LOGDEBUG("Enabler Switch = $state.enablerSwitch2")
    if(state.enablerSwitch2 == "on"){
    	LOGDEBUG("Enabler Switch is ON - Child app is disabled.")
	} else {
		LOGDEBUG("Enabler Switch is OFF - Child app is active.")
    }
}

def modeHandler(evt){
	allInactive()
	LOGDEBUG("       - - - - - -     ")
	LOGDEBUG("In modeHandler...Before if's - Thinks it's Mode: ${state.modeNow} - Actual mode: ${location.mode}")
	state.prevMode = state.modeNow
	state.modeNow = location.mode
	if(state.modeNow.contains(modeName1)){
		state.currentMode = "1"
		state.dimTime = dimTime1
		state.offTime = offTime1
		LOGDEBUG("In modeHandler...Match Found - modeName1: ${modeName1} modeNow: ${state.modeNow}-So state.currentMode: ${state.currentMode} ")
		if(state.allInactive == "false") {
			if(state.prevMode != state.modeNow) {setScene()}
		}
	}
	else
		if(state.modeNow.contains(modeName2)){
			state.currentMode = "2"
			state.dimTime = dimTime2
			state.offTime = offTime2
			LOGDEBUG("In modeHandler...Match Found - modeName2: ${modeName2} modeNow: ${state.modeNow}-So state.currentMode: ${state.currentMode}")
			if(state.allInactive == "false") {
				if(state.prevMode != state.modeNow) {setScene()}
			}
		}
	else
		if(state.modeNow.contains(modeName3)){
			state.currentMode = "3"
			state.dimTime = dimTime3
			state.offTime = offTime3
			LOGDEBUG("In modeHandler...Match Found - modeName3: ${modeName3} modeNow: ${state.modeNow}-So state.currentMode: ${state.currentMode}")
			if(state.allInactive == "false") {
				if(state.prevMode != state.modeNow) {setScene()}
			}
		}
	else
		if(state.modeNow.contains(modeName4)){
			state.currentMode = "4"
			state.dimTime = dimTime4
			state.offTime = offTime4
			LOGDEBUG("In modeHandler...Match Found - modeName4: ${modeName4} modeNow: ${state.modeNow}-So state.currentMode: ${state.currentMode}")
			if(state.allInactive == "false") {
				if(state.prevMode != state.modeNow) {setScene()}
			}
		}
	else
		if(state.modeNow.contains(modeName5)){
			state.currentMode = "5"
			state.dimTime = dimTime5
			state.offTime = offTime5
			LOGDEBUG("In modeHandler...Match Found - modeName5: ${modeName5} modeNow: ${state.modeNow}-So state.currentMode: ${state.currentMode} ")
			if(state.allInactive == "false") {
				if(state.prevMode != state.modeNow) {setScene()}
			}
		}
	else
		if(state.modeNow.contains(modeName6)){
			state.currentMode = "6"
			state.dimTime = dimTime6
			state.offTime = offTime6
			LOGDEBUG("In modeHandler...Match Found - modeName6: ${modeName6} modeNow: ${state.modeNow}-So state.currentMode: ${state.currentMode} ")
			if(state.allInactive == "false") {
				if(state.prevMode != state.modeNow) {setScene()}
			}
		}
	else
		if(state.modeNow.contains(modeName7)){
			state.currentMode = "7"
			state.dimTime = dimTime7
			state.offTime = offTime7
			LOGDEBUG("In modeHandler...Match Found - modeName7: ${modeName7} modeNow: ${state.modeNow}-So state.currentMode: ${state.currentMode} ")
			if(state.allInactive == "false") {
				if(state.prevMode != state.modeNow) {setScene()}
			}
		}
	else
		if(state.modeNow.contains(modeName8)){
			state.currentMode = "8"
			state.dimTime = dimTime8
			state.offTime = offTime8
			LOGDEBUG("In modeHandler...Match Found - modeName8: ${modeName8} modeNow: ${state.modeNow}-So state.currentMode: ${state.currentMode} ")
			if(state.allInactive == "false") {
				if(state.prevMode != state.modeNow) {setScene()}
			}
		}
	else{
		LOGDEBUG("In modeHandler...Something went wrong, no Mode matched!")
		state.currentMode = "NONE"
	}
}

def allInactive(){
	LOGDEBUG("       - - - - - -     ")
	LOGDEBUG("In allInactive...")
    state.allInactive = "true"
	motionSensors.each {eachMotion->
        if(eachMotion.currentValue("motion") == "active"){
            state.allInactive = "false"
			LOGDEBUG("In allInactive...allInactive: ${state.allInactive}")
		} else {
			LOGDEBUG("In allInactive...allInactive: ${state.allInactive}")
		}
	}
}

def motionHandler(evt) {
	LOGDEBUG("       - - - - - -     ")
	LOGDEBUG("In motionHandler...")
	if(pause1 == true){log.warn "Unable to continue - App paused"}
    if(pause1 == false){LOGDEBUG("Continue - App NOT paused")
		modeHandler()
		LOGDEBUG("In motionHandler...Mode: ${state.modeNow}")
		if(!enableSwitch1 || enableSwitch.currentValue("switch") == "on"){
    		if(evt.value == "inactive" && state.allInactive == "true") {
        		LOGDEBUG("Inactive received, Starting timers")
				dimTimeSec = (state.dimTime * 60)
				LOGDEBUG("Time to dimLights ${dimTimeSec}")
    			runIn(dimTimeSec, dimLights)
    		}
    		if(evt.value == "active") {
        		state.lastActive = now()
        		log.info "Motion activated, Setting Scene"
				modeHandler()
    			setScene()
    		}
		}
	}
}

def setScene() {
	luxLevel()
	LOGDEBUG("       - - - - - -     ")
	LOGDEBUG("In setScene...Mode is ${state.currentMode} Mode: ${state.modeNow}")
	if(state.isItDark == "true") {
		if(state.currentMode == "1"){
			LOGDEBUG("In setScene...1: currentMode: ${state.currentMode}")
			if(state.prevMode == "2") sceneSwitch2.off()
			if(state.prevMode == "3") sceneSwitch3.off()
			if(state.prevMode == "4") sceneSwitch4.off()
			if(state.prevMode == "5") sceneSwitch5.off()
			if(state.prevMode == "6") sceneSwitch6.off()
			if(state.prevMode == "7") sceneSwitch7.off()
			if(state.prevMode == "8") sceneSwitch8.off()
			sceneSwitch1.on()
		} else
		if(state.currentMode == "2"){
			LOGDEBUG("In setScene...2: currentMode: ${state.currentMode}")
			if(state.prevMode == "1") sceneSwitch1.off()
			if(state.prevMode == "3") sceneSwitch3.off()
			if(state.prevMode == "4") sceneSwitch4.off()
			if(state.prevMode == "5") sceneSwitch5.off()
			if(state.prevMode == "6") sceneSwitch6.off()
			if(state.prevMode == "7") sceneSwitch7.off()
			if(state.prevMode == "8") sceneSwitch8.off()
			sceneSwitch2.on()
		} else
		if(state.currentMode == "3"){
			LOGDEBUG("In setScene...3: currentMode: ${state.currentMode}")
			if(state.prevMode == "1") sceneSwitch1.off()
			if(state.prevMode == "2") sceneSwitch2.off()
			if(state.prevMode == "4") sceneSwitch4.off()
			if(state.prevMode == "5") sceneSwitch5.off()
			if(state.prevMode == "6") sceneSwitch6.off()
			if(state.prevMode == "7") sceneSwitch7.off()
			if(state.prevMode == "8") sceneSwitch8.off()
			sceneSwitch3.on()
		} else
		if(state.currentMode == "4"){
			LOGDEBUG("In setScene...4: currentMode: ${state.currentMode}")
			if(state.prevMode == "1") sceneSwitch1.off()
			if(state.prevMode == "2") sceneSwitch2.off()
			if(state.prevMode == "3") sceneSwitch3.off()
			if(state.prevMode == "5") sceneSwitch5.off()
			if(state.prevMode == "6") sceneSwitch6.off()
			if(state.prevMode == "7") sceneSwitch7.off()
			if(state.prevMode == "8") sceneSwitch8.off()
			sceneSwitch4.on()
		} else
		if(state.currentMode == "5"){
			LOGDEBUG("In setScene...5: currentMode: ${state.currentMode}")
			if(state.prevMode == "1") sceneSwitch1.off()
			if(state.prevMode == "2") sceneSwitch2.off()
			if(state.prevMode == "3") sceneSwitch3.off()
			if(state.prevMode == "4") sceneSwitch4.off()
			if(state.prevMode == "6") sceneSwitch6.off()
			if(state.prevMode == "7") sceneSwitch7.off()
			if(state.prevMode == "8") sceneSwitch8.off()
			sceneSwitch5.on()
		} else
		if(state.currentMode == "6"){
			LOGDEBUG("In setScene...6: currentMode: ${state.currentMode}")
			if(state.prevMode == "1") sceneSwitch1.off()
			if(state.prevMode == "2") sceneSwitch2.off()
			if(state.prevMode == "3") sceneSwitch3.off()
			if(state.prevMode == "4") sceneSwitch4.off()
			if(state.prevMode == "5") sceneSwitch5.off()
			if(state.prevMode == "7") sceneSwitch7.off()
			if(state.prevMode == "8") sceneSwitch8.off()
			sceneSwitch6.on()
		} else
		if(state.currentMode == "7"){
			LOGDEBUG("In setScene...7: currentMode: ${state.currentMode}")
			if(state.prevMode == "1") sceneSwitch1.off()
			if(state.prevMode == "2") sceneSwitch2.off()
			if(state.prevMode == "3") sceneSwitch3.off()
			if(state.prevMode == "4") sceneSwitch4.off()
			if(state.prevMode == "5") sceneSwitch5.off()
			if(state.prevMode == "6") sceneSwitch6.off()
			if(state.prevMode == "8") sceneSwitch8.off()
			sceneSwitch7.on()
		} else
		if(state.currentMode == "8"){
			LOGDEBUG("In setScene...8: currentMode: ${state.currentMode}")
			if(state.prevMode == "1") sceneSwitch1.off()
			if(state.prevMode == "2") sceneSwitch2.off()
			if(state.prevMode == "3") sceneSwitch3.off()
			if(state.prevMode == "4") sceneSwitch4.off()
			if(state.prevMode == "5") sceneSwitch5.off()
			if(state.prevMode == "6") sceneSwitch6.off()
			if(state.prevMode == "7") sceneSwitch7.off()
			sceneSwitch8.on()
		} else	
		if(state.currentMode == "NONE"){
			LOGDEBUG("In setScene...Something went wrong, no Mode matched!")
		}
	} else{
		log.info "It's too light in here, no Scenes activated."
	}
}

def luxLevel() {
	LOGDEBUG("       - - - - - -     ")
    LOGDEBUG("In luxLevel...")
    if (lightSensor != null) {
		if(lightLevel == null) {lightLevel = 0}
        def curLev = lightSensor.currentValue("illuminance").toInteger()
        if (curLev >= lightLevel.toInteger()) {
            LOGDEBUG("In luxLevel...Current Light Level: ${curLev} is greater than lightValue: ${lightLevel}")
			state.isItDark = "false"
        } else {
            LOGDEBUG("In luxLevel...Current Light Level: ${curLev} is less than lightValue: ${lightLevel}")
			state.isItDark = "true"
        }
    }
}

def dimLights() {
	LOGDEBUG("       - - - - - -     ")
	LOGDEBUG("In dimLights...Mode: ${state.modeNow}")
	dimTimeSec = (state.dimTime * 60)
	safetyTimeSec = (safetyTime * 60)
	LOGDEBUG("In dimLights...dimTimeSec: ${dimTimeSec}")
    def delta = (now() - (state.lastActive ?:0))/1000
    if(delta < dimTimeSec) {
        LOGDEBUG("In dimLights...Time Since Last Active = ${delta} less than time to Dim = ${dimTimeSec}")
		setScene()
    } else {
		LOGDEBUG("In dimLights...Time Since Last Active = ${delta} greater than time to Dim = ${dimTimeSec}")
		if(state.currentMode == "1") {lightsToDim = lightsToDim1}
		if(state.currentMode == "2") {lightsToDim = lightsToDim2}
		if(state.currentMode == "3") {lightsToDim = lightsToDim3}
		if(state.currentMode == "4") {lightsToDim = lightsToDim4}
		if(state.currentMode == "5") {lightsToDim = lightsToDim5}
		if(state.currentMode == "6") {lightsToDim = lightsToDim6}
		if(state.currentMode == "7") {lightsToDim = lightsToDim7}
		if(state.currentMode == "8") {lightsToDim = lightsToDim8}
		if(state.currentMode != "NONE") {
        	lightsToDim.each {eachLight->
				startLevel = eachLight.currentLevel.toInteger()
				setDimLevel1 = (startLevel * 0.5)
				setDimLevel = (startLevel - setDimLevel1)
				LOGDEBUG("In dimLights...eachlight: ${eachLight}, startLevel: ${startLevel}, dimLevel: ${setDimLevel} and is ${eachLight.currentSwitch}")
				log.info "Motion has been inactive for ${delta} seconds - Starting Dim Warning"
    			if(eachLight.currentSwitch == "on") eachLight.setLevel(setDimLevel)
				runIn(state.offTime, setOff)
				runIn(safetyTimeSec, safetyNet)
			}
		} else{
			LOGDEBUG("In dimLights...Something went wrong, no Mode matched!")
		}
	}
}

def setOff() {
	LOGDEBUG("       - - - - - -     ")
	LOGDEBUG("In setOff...currentMode: ${state.currentMode} Mode: ${state.modeNow}")
    def delta = (now() - (state.lastActive ?:0))/1000
    if(delta < state.offTime) {
        LOGDEBUG("In setOff...Cancelling setOff: Time Since Last Active: ${delta} and OffTime: ${state.offTime}")
	} else {
		if(sceneSwitch1 != null) sceneSwitch1.off()
		if(sceneSwitch2 != null) sceneSwitch2.off()
		if(sceneSwitch3 != null) sceneSwitch3.off()
		if(sceneSwitch4 != null) sceneSwitch4.off()
		if(sceneSwitch5 != null) sceneSwitch5.off()
		if(sceneSwitch6 != null) sceneSwitch6.off()
		if(sceneSwitch7 != null) sceneSwitch7.off()
		if(sceneSwitch8 != null) sceneSwitch8.off()
	}
}

def safetyNet() {
	LOGDEBUG("       - - - - - -     ")
	LOGDEBUG("In safetyNet...Mode: ${state.modeNow}")
	safetyTimeSec = (safetyTime * 60)
    def delta = (now() - (state.lastActive ?:0))/1000
    if(delta < safetyTimeSec) {
        LOGDEBUG("In safetyNet...Cancelling safetyNet: Time Since Last Active= ${delta} and Off Window = ${safetyTimeSec}")
	} else {
		log.info "Motion Safety Net - turning all selected lights off"
		safetySwitches.off()
	}
}

// ********** Normal Stuff **********

def pauseOrNot(){
    state.pauseNow = pause1
        if(state.pauseNow == true){
            state.pauseApp = true
            if(app.label){
            if(app.label.contains('red')){
                log.warn "Paused"}
            else{app.updateLabel(app.label + ("<font color = 'red'> (Paused) </font>" ))
              LOGDEBUG("App Paused - state.pauseApp = $state.pauseApp ")   
            }
            }
        }
     if(state.pauseNow == false){
         state.pauseApp = false
         if(app.label){
     		if(app.label.contains('red')){ app.updateLabel(app.label.minus("<font color = 'red'> (Paused) </font>" ))
     		LOGDEBUG("App Released - state.pauseApp = $state.pauseApp ")                          
          	}
         }
	}      
}

def setDefaults(){
    pauseOrNot()
    if(pause1 == null){pause1 = false}
    if(state.pauseApp == null){state.pauseApp = false}
	if(logEnable == null){logEnable = false}
	if(state.enablerSwitch2 == null){state.enablerSwitch2 = "off"}
	if(state.currentMode == null){state.currentMode = "NONE"}
}

def logCheck(){
	state.checkLog = logEnable
	if(state.logEnable == true){
		log.info "${app.label} - All Logging Enabled"
	}
	else if(state.logEnable == false){
		log.info "${app.label} - Further Logging Disabled"
	}
}

def LOGDEBUG(txt){
    try {
		if (settings.logEnable) { log.debug("${app.label} - ${txt}") }
    } catch(ex) {
    	log.error("${app.label} - LOGDEBUG unable to output requested data!")
    }
}

def display(){
	section{paragraph "<b>Motion Controlled Scene Lighting</b><br>App Version: 1.0.0<br>@BPTWorld"}      
	section(){input "pause1", "bool", title: "Pause This App", required: true, submitOnChange: true, defaultValue: false }
} 
