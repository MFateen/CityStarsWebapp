export class SpareType {
  id: number;
  serialNo: number;
  name: string;
  unit: string;
}

export class Spare {
  id: number;
  serialNo: number;
  available: boolean;
  spareType: SpareType;
  quantity: number;
  spareTypeID: number;
}

export class VoucherItem {
  spareType: SpareType;
  refCode: string;
  quantity: number;
  make: string;
  unit: string;
  description: string;
  notes: string;
}

export class PurchaseRequest {
  date: Date;
  needsRequest: string;
  supplierCode: string;
  supplierName: string;
  voucherItemRequests: VoucherItem[];
  // voucherItems: VoucherItem[];
}

export class StockReceived {
  date: Date;
  needsRequest: string;
  supplierCode: string;
  supplierName: string;
  purchaseOrder: number;
  voucherItemRequests: VoucherItem[];
}

export class StockIssue {
  busID: number;
  date: Date;
  voucherItemRequests: VoucherItem[];
}
