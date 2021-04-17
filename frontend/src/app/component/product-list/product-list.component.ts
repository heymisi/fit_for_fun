import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CartItem } from 'src/app/common/cart-item';
import { FilterEvent } from 'src/app/common/filter-event';
import { Product } from 'src/app/common/product';
import { Sport } from 'src/app/common/sport';
import { TransactionItem } from 'src/app/common/transaction-item';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  products: Product[] = [];
  currentCategoryId: number = 1;
  previousCategoryId: number = 1;
  searchMode: boolean = false;

  //pagination
  pageNumber: number = 1;
  pageSize: number = 9;
  totalElements: number = 0;

  previousKeyword: string = null;
  
  filters : FilterEvent = null;

  selectedPageSize: any;
  selectedSort: string = null;
  constructor(private productService: ProductService,
    private route: ActivatedRoute,
    private cartService: CartService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(() => {
      this.listProducts();
    });
  }

  listProducts() {
    this.searchMode = this.route.snapshot.paramMap.has("keyword");
    if (this.searchMode) {
      this.handleSearchProducts();
    } else {
      this.handleListProducts();
    }
  }
  handleListProducts() {
    this.productService.getProductListPaginate(this.pageNumber - 1,
      this.pageSize,
      this.currentCategoryId,
      this.selectedSort,
      this.filters)
      .subscribe(this.proccessResult());
    window.scroll(0, 0);
  }
  proccessResult() {
    return data => {
      this.products = data.content;
      this.pageNumber = data.number + 1;
      this.pageSize = data.size;
      this.totalElements = data.totalElements;
    }
  }

  handleSearchProducts() {
    const theKeyword: string = this.route.snapshot.paramMap.get("keyword");
    if (this.previousKeyword != theKeyword) {
      this.pageNumber = 1;
    }
    this.previousKeyword = theKeyword;
    this.productService.searchProductsPaginate(this.pageNumber - 1,
                                              this.pageSize,
                                              theKeyword).subscribe(this.proccessResult());
  }

  updatePageSize(pageSize: string) {
    this.pageSize = +pageSize;
    this.pageNumber = 1;
    this.listProducts();
  }

  sortPage(){
    this.pageNumber = 1;
    this.listProducts();
  }

  addToCart(product: Product){
    const cartItem = new TransactionItem();
    cartItem.shopItem = product;
    console.log(product.category);
    this.productService.getProductCategoryById(+product.category).subscribe(data => product.category = data);
    cartItem.quantity = 1;
    console.log(cartItem);
    this.cartService.addToCart(cartItem);
  }

  onFiltered(filter: FilterEvent){
    this.filters = filter;
    this.listProducts();
  }
}
