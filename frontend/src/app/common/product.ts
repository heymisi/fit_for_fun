import { CartItem } from "./cart-item";
import { ProductCategory } from "./product-category";
import { Sport } from "./sport";
import { TransactionItem } from "./transaction-item";

export class Product {
    id?: string;
    name?: string;
    price?: number;
    dateCreated?: Date;
    lastUpdated?: Date;
    unitsInStock?: number;
    description?: string;
    imageUrl?: string;
    category?: ProductCategory;
    rating?: number;
    image?: File;
    sportType?: Sport;

}
