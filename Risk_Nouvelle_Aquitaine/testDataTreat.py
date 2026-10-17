# test_traitement.py
import pandas as pd
import pytest

# Since there are no functions in the dataTreating file, we test some parts of the code of the file. 
# To launch the python tests type pytest testDataTreat.py -v in the terminal 

#tests on date slicing

def test_aaaammjj_to_mm_yyyy():
    date = "20230415"
    result = f"{date[4:6]}-{date[0:4]}"
    assert result == "04-2023"

def test_aaaamm_to_mm_yyyy():
    date = "202304"
    result = f"{date[4:6]}-{date[0:4]}"
    assert result == "04-2023"

# tests on group by and means

def test_mean_temp_per_month():
    df = pd.DataFrame({
        "NUM_POSTE": ["33009", "33009", "33009"],
        "NOM_USUEL": ["ARCACHON", "ARCACHON", "ARCACHON"],
        "AAAAMMJJ": [20230101, 20230115, 20230201],
        "TN": [5.0, 7.0, 3.0],
        "TX": [15.0, 17.0, 10.0],
        "TM": [10.0, 12.0, 6.0],
    })
    df["AAAAMM"] = df["AAAAMMJJ"].astype(str).str[:6]
    result = df.groupby(["NUM_POSTE", "NOM_USUEL", "AAAAMM"])[["TN", "TX", "TM"]].mean().reset_index()

    assert len(result) == 2  
    jan = result[result["AAAAMM"] == "202301"]
    assert jan["TN"].values[0] == 6.0   
    assert jan["TX"].values[0] == 16.0  

def test_mean_rain_per_month():
    df = pd.DataFrame({
        "NUM_POSTE": ["33009", "33009"],
        "NOM_USUEL": ["ARCACHON", "ARCACHON"],
        "AAAAMMJJ": [20230101, 20230115],
        "RR": [10.0, 20.0],
        "DRR": [2.0, 4.0],
    })
    df["AAAAMM"] = df["AAAAMMJJ"].astype(str).str[:6]
    result = df.groupby(["NUM_POSTE", "NOM_USUEL", "AAAAMM"])[["RR", "DRR"]].mean().reset_index()

    assert result["RR"].values[0] == 15.0
    assert result["DRR"].values[0] == 3.0

# Tests on the sorting of datas

def test_drop_duplicates_towns():
    df = pd.DataFrame({
        "NUM_POSTE": ["33009", "33009", "33010"],
        "NOM_USUEL": ["ARCACHON", "ARCACHON", "BORDEAUX"],
        "LAT": [44.66, 44.66, 44.83],
        "LON": [-1.15, -1.15, -0.57],
    })
    result = df.drop_duplicates(subset="NUM_POSTE").dropna(subset="NUM_POSTE")
    assert len(result) == 2

def test_num_poste_slicing():
    df = pd.DataFrame({"NUM_POSTE": [33009001, 33009004]})
    df["NUM_POSTE"] = df["NUM_POSTE"].astype(str).str[:-3]
    assert df["NUM_POSTE"].tolist() == ["33009", "33009"]

def test_cod_commune_length_filter():
    df = pd.DataFrame({"cod_commune": ["33001", "3300", "330012", "16005"]})
    result = df[df["cod_commune"].str.len() == 5]
    assert len(result) == 2

def test_cod_commune_startswith_filter():
    df = pd.DataFrame({"cod_commune": ["33001", "16005", "75001"]})
    na_codes = ("33", "16")
    result = df[df["cod_commune"].str.startswith(na_codes)]
    assert len(result) == 2
    assert "75001" not in result["cod_commune"].values