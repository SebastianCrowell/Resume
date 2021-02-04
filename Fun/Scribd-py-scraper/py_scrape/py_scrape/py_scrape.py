import requests
import sys
import urllib.request
from selenium import webdriver
from requests import get
from itertools import count
from sys import argv
from bs4 import BeautifulSoup

url = input("Please input a scribd url with images in .jpg format: ")

# Obtain the url for the site with the things wanted, check with GET request (200)
response = requests.get(url)
print(response)

driver = webdriver.Chrome("./assets/chromedriver.exe")
driver.get(url)

pages = []
for i in range(1, 4, 1):
    page = driver.find_element_by_id('page' + str(i))
    soup = BeautifulSoup(page.get_attribute('innerHTML'), 'html.parser')
    #print(soup) orig? src?
    page = soup.select('.absimg')
    pages.append(page)
    print(page)

scrape = open("./assets/scrape.txt","w")
scrape.write(str(pages))
scrape.close()