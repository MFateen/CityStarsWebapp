export class Bus {
  condition: number;
  createdAt: Date;
  createdBy: string;
  events: string[];
  id: number;
  inOperation: boolean;
  name: string;
  platesLetters: string;
  platesNumbers: string;
  updatedAt: Date;
  updatedBy: string;
  km: number;
}

export enum BusEventType {
  SIGNAL_ISSUE = 'SIGNAL_ISSUE',
  RESOLVE_ISSUE = 'RESOLVE_ISSUE',
  GENERAL = 'GENERAL',
  TRIP_SCHEDULED = 'TRIP_SCHEDULED',
  SPARE_PART_ADDED = 'SPARE_PART_ADDED'
}

export class BusEvent {
  createdBy: string;
  createdAt: Date;
  text: string;
  type: BusEventType;
  busId: number;
  busCondition: number;
}
