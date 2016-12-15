/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  EmonTH Temperature Renderer
 *
 *  Author: Steve Short
 */
metadata {
	definition (name: "EmonTH Temperature Sensor", namespace: "stshort", author: "Steve Short") {
		capability "Temperature Measurement"
		capability "Sensor"
		
		command "setTemperature", ["number"]
	}

	// UI tile definitions
	tiles {
		valueTile("temperature", "device.temperature", width: 2, height: 2) {
			state("temperature", label:'${currentValue}', unit:"C",
				backgroundColors:[
					[value:  0, color: "#153591"],
					[value:  2, color: "#1e9cbb"],
					[value:  5, color: "#90d2a7"],
					[value: 10, color: "#44b621"],
					[value: 20, color: "#f1d801"],
					[value: 30, color: "#d04e00"],
					[value: 40, color: "#bc2323"]
				]
			)
		}
		main "temperature"
		details("temperature")
	}
}

// Parse incoming device messages to generate events
def parse(String description) {
	
	def value = description
	def descriptionText = "${device.displayName} value ${value} °C"

	log.debug descriptionText
	
	def map = [
		name: 'temperature',
		value: value,
		descriptionText: descriptionText,
		isStateChange: true,
		translatable: true,
		displayed: true,
		unit: "C"
	]
	
	def result = createEvent (map)
	log.debug "Parse returned ${result?.descriptionText}"
	
	return result
}

def setTemperature(value) {
	def descriptionText = "${device.displayName} value ${value} °C"
    log.debug "setTemperature ${descriptionText}"
	sendEvent(name:"temperature", value: value, descriptionText: descriptionText, displayed: true, unit: "C", isStateChange: true)
}
